import sys

def apply_choice(pss, row, col, val):
	mask = valid_masks[val]
	if pss.val_set[row][col] != 0:
		print("apply_choice: row " + str(row) + " col " + str(col) + " val " + str(val) + " already set to " + str(pss.val_set[row][col]))
		return -1
	pss.val_set[row][col] = val
	boxr = row // 3
	boxc = col // 3
	for j in range(9):
		if (pss.avail_mask[row][j] & mask) != 0:
			pss.box_avail_counts[boxr][j // 3][val - 1] -= 1
			pss.col_avail_counts[j][val - 1] -= 1
		pss.avail_mask[row][j] &= ~mask
	for i in range(9):
		if (pss.avail_mask[i][col] & mask) != 0:
			pss.box_avail_counts[i // 3][boxc][val - 1] -= 1
			pss.row_avail_counts[i][val - 1] -= 1
		pss.avail_mask[i][col] &= ~mask
	boxr = row // 3
	boxc = col // 3
	for i in range(3 * boxr, 3 * (boxr + 1)):
		for j in range(3 * boxc, 3 * (boxc + 1)):
			if (pss.avail_mask[i][j] & mask) != 0:
				pss.col_avail_counts[j][val - 1] -= 1
				pss.row_avail_counts[i][val - 1] -= 1
			pss.avail_mask[i][j] &= ~mask
	for i in range(1, 10):
		if i != val and (pss.avail_mask[row][col] & valid_masks[i]) != 0:
			pss.box_avail_counts[row // 3][col // 3][i - 1] -= 1
			pss.col_avail_counts[col][i - 1] -= 1
			pss.row_avail_counts[row][i - 1] -= 1
	pss.avail_mask[row][col] = mask
	pss.row_avail_counts[row][val - 1] = 32
	pss.col_avail_counts[col][val - 1] = 32
	pss.box_avail_counts[boxr][boxc][val - 1] = 32
	return 0

def find_next_test(pss, psd):
	mask = valid_masks[psd.solve_val]
	if psd.solve_index >= psd.solve_cnt:
		return -1
	if psd.solve_type == STYP_ROW:
		if psd.solve_index == 0:
			startj = 0
		else:
			startj = psd.test_col + 1
		i = psd.solve_row
		for j in range(startj, 9):
			if (pss.avail_mask[i][j] & mask) != 0:
				psd.test_col = j
				psd.test_row = i
				psd.solve_index += 1
				return 0
		return -1
	elif psd.solve_type == STYP_COL:
		if psd.solve_index == 0:
			starti = 0
		else:
			starti = psd.test_row + 1
		j = psd.solve_col
		for i in range(starti, 9):
			if (pss.avail_mask[i][j] & mask) != 0:
				psd.test_col = j
				psd.test_row = i
				psd.solve_index += 1
				return 0
		return -1
	elif psd.solve_type == STYP_BOX:
		if psd.solve_index == 0:
			starti = 0
			startj = 0
		else:
			starti = psd.test_row - 3 * psd.solve_row
			startj = psd.test_col + 1 - 3 * psd.solve_col
		for i in range(starti, 3):
			for j in range(startj, 3):
				if (pss.avail_mask[i + 3 * psd.solve_row][j + 3 * psd.solve_col] & mask) != 0:
					psd.test_col = j + 3 * psd.solve_col
					psd.test_row = i + 3 * psd.solve_row
					psd.solve_index += 1
					return 0
		return -1
	else:
		print("bad solve type " + str(psd.solve_type))
		return -1

def get_solve_step(pss, psd):
	psd.solve_cnt = 10
	for i in range(9):
		for j in range(9):
			if pss.row_avail_counts[i][j] < psd.solve_cnt:
				psd.solve_cnt = pss.row_avail_counts[i][j]
				psd.solve_type = STYP_ROW
				psd.solve_row = i
				psd.solve_val = j + 1
	for i in range(9):
		for j in range(9):
			if pss.col_avail_counts[i][j] < psd.solve_cnt:
				psd.solve_cnt = pss.col_avail_counts[i][j]
				psd.solve_type = STYP_COL
				psd.solve_col = i
				psd.solve_val = j + 1
	for i in range(3):
		for j in range(3):
			for k in range(9):
				if pss.box_avail_counts[i][j][k] < psd.solve_cnt:
					psd.solve_cnt = pss.box_avail_counts[i][j][k]
					psd.solve_type = STYP_BOX
					psd.solve_row = i
					psd.solve_col = j
					psd.solve_val = k + 1
	if psd.solve_cnt == 0:
		return -1
	else:
		return 0

def solve(level):
	pss = states[level]
	sd = SOLVE_DATA()
	if get_solve_step(pss, sd) != 0:
		return -1
	sd.solve_index = 0
	while find_next_test(pss, sd) == 0:
		if level == 80:
			pss.val_set[sd.test_row][sd.test_col] = sd.solve_val
			return 0
		else:
			pssnxt = states[level + 1]
			pssnxt = pss
			if apply_choice(pssnxt, sd.test_row, sd.test_col, sd.solve_val) == 0:
				if check_constraints(pssnxt) == 0:
					if solve(level + 1) == 0:
						for i in range(9):
							for j in range(9):
								pss.val_set[i][j] = pssnxt.val_set[i][j]
						return 0
	return -1

def check_equal(baseMask, chkMask):
	result = 0
	if (valid_masks[5] & baseMask) != 0:
		result |= valid_masks[5]
	for i in range(1, 10):
		if (valid_masks[i] & chkMask) == 0 and (valid_masks[10 - i] & baseMask) != 0:
			result |= valid_masks[10 - i]
	return result

def check_less(baseMask, chkMask):
	result = 0
	if (valid_masks[9] & baseMask) != 0:
		result |= valid_masks[9]
	for i in range(1, 9):
		if (valid_masks[i] & chkMask) != 0:
			break
		elif (valid_masks[9 - i] & baseMask) != 0:
			result |= valid_masks[9 - i]
	return result
	
def check_greater(baseMask, chkMask):
	result = 0
	if (valid_masks[1] & baseMask) != 0:
		result |= valid_masks[1]
	for i in range(9, 2, -1):
		if (valid_masks[i] & chkMask) != 0:
			break
		elif (valid_masks[11 - i] & baseMask) != 0:
			result |= valid_masks[11 - i]
	return result

def check_constraint(constraint, baseMask, chkMask):
	if constraint < 0:
		return check_less(baseMask, chkMask)
	elif constraint > 0:
		return check_greater(baseMask, chkMask)
	else:
		return check_equal(baseMask, chkMask)

def check_constraints(pss):
	i = row = col = baseConsRow = baseConsCol = scan_count = change_count = 1
	scan_count = 0
	while change_count > 0:
		scan_count += 1
		change_count = 0
		baseConsRow = 0
		for row in range(9):
			baseConsCol = 0
			for col in range(9):
				if pss.val_set[row][col] == 0:
					baseMask = pss.avail_mask[row][col]
					totResult = 0
					if col % 3 != 0:
						chkMask = pss.avail_mask[row][col-1]
						resultMask = check_constraint(constraints[baseConsRow][baseConsCol - 1], baseMask, chkMask)
						if resultMask != 0:
							baseMask &= ~resultMask
							change_count += 1
							totResult |= resultMask
					if col % 3 != 2:
						chkMask = pss.avail_mask[row][col + 1]
						resultMask = check_constraint(constraints[baseConsRow][baseConsCol], baseMask, chkMask)
						if resultMask != 0:
							baseMask &= ~resultMask
							change_count += 1
							totResult |= resultMask
					if row % 3 != 0:
						chkMask = pss.avail_mask[row - 1][col]
						resultMask = check_constraint(constraints[baseConsRow - 1][col], baseMask, chkMask)
						if resultMask != 0:
							baseMask &= ~resultMask
							change_count += 1
							totResult |= resultMask
					if row % 3 != 2:
						chkMask = pss.avail_mask[row + 1][col]
						resultMask = check_constraint(constraints[baseConsRow + 1][col], baseMask, chkMask)
						if resultMask != 0:
							baseMask &= ~resultMask
							change_count += 1
							totResult |= resultMask
					if baseMask == 0:
						return -1
					pss.avail_mask[row][col] = baseMask 
					if totResult != 0:
						for i in range(9):
							if (valid_masks[i] & totResult) != 0:
								pss.col_avail_counts[col][i - 1] -= 1
								pss.row_avail_counts[row][i - 1] -= 1
								pss.box_avail_counts[row // 3][col // 3][i - 1] -= 1
				if col % 3 != 2:
					baseConsCol += 1
			if row % 3 != 2:
				baseConsRow += 2
			else:
				baseConsRow += 1
	return 0

def scan_convert(prow, n, s):
	for i in range(n):
		c = s[i]
		if c == "<":
			prow[i] = -1
		elif c == "=":
			prow[i] = 0
		elif c == ">":
			prow[i] = 1

def scan_constraints():
	global index
	for i in range(3):
		for j in range(3):
			line = f_split[index]
			index += 1
			scan_convert(constraints[5 * i + 2 * j], 6, line)
			if j < 2:
				line = f_split[index]
				index += 1
				scan_convert(constraints[5 * i + 2 * j + 1], 9, line)

def search_init():
	pss = states[0]
	for i in range(9):
		for j in range(9):
			pss.avail_mask[i][j] = ALL_MASK
			pss.val_set[i][j] = 0
			pss.row_avail_counts[i][j] = 9
			pss.col_avail_counts[i][j] = 9
	for i in range(3):
		for j in range(3):
			for k in range(9):
				pss.box_avail_counts[i][j][k] = 9
	
class SEARCH_STATE(object):
	def __init__(self):
		self.avail_mask = [[0 for x in range(9)] for y in range(9)]
		self.row_avail_counts = [[0 for x in range(9)] for y in range(9)]
		self.col_avail_counts = [[0 for x in range(9)] for y in range(9)]
		self.box_avail_counts = [[[0 for x in range(9)] for x in range(3)] for y in range(3)]
		self.val_set = [[0 for x in range(9)] for y in range(9)]
		
class SOLVE_DATA(object):
	def __init__(self):
		self.solve_type = 0
		self.solve_val = 0
		self.solve_row = 0
		self.solve_col = 0
		self.solve_cnt = 0
		self.solve_index = 0
		self.test_row = 0
		self.test_col = 0

if len(sys.argv) != 2:
	print("Invalid number of arguments.")
	quit()
	
valid_masks = [0, 1, 2, 4, 8, 16, 32, 64, 128, 256]
STYP_ROW = 1
STYP_COL = 2
STYP_BOX = 3
ALL_MASK = 511
constraints = [None] * 15
for i in range(15):
	constraints[i] = [None] * 9
states = [None] * 81
for i in range(81):
	states[i] = SEARCH_STATE()
	
file_name = sys.argv[1]

with open(file_name, "r") as f:
	f_contents = f.read()
	
f_split = f_contents.split("\n")
nprob = int(f_split[0])
index = 2

for curprob in range(1, nprob + 1):
	search_init()
	scan_constraints()
	#print(constraints)
	#quit()
	if check_constraints(states[0]) != 0:
		print("problem index " + str(curprob) + " init check constraints failed")
	if solve(0) != 0:
		print("error")
	else:
		print(str(curprob))
		for i in range(9):
			for j in range(9):
				sys.stdout.write(str(states[0].val_set[i][j]) + " ")
			sys.stdout.write("\n")