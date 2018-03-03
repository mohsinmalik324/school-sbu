import sys
import queue

def print_soln(s):
	for i in range(1, len(s.grid) - 1):
		for j in range(1, len(s.grid[i]) - 1):
			if j != 1:
				sys.stdout.write(" ")
			print_square(s.grid[i][j])
		sys.stdout.write("\n")
		
def print_square(s):
	sys.stdout.write(s.val)

def adjacent_okay(s, val, r, c):
	for dr in range(-1, 2):
		for dc in range(-1, 2):
			if s.grid[r + dr][c + dc].val == val:
				return False
	return True

def attempt(s):
	if s.to_do.empty():
		return True
	curr = s.to_do.get()
	row = curr.row
	col = curr.col
	avail = curr.avail
	for c in avail:
		if adjacent_okay(s, c, row, col):
			if c in curr.avail:
				curr.avail.remove(c)
			s.grid[row][col].val = c
			if attempt(s):
				return True
			s.grid[row][col].val = "-"
			curr.avail.add(c)
	s.to_do.put(curr)
	return False

def init_solution(s):
	global index
	line_split = f_split[index].split(" ")
	r = int(line_split[1])
	c = int(line_split[2])
	for i in range(0, r + 2):
		s.grid.append([])
		for j in range(0, c + 2):
			s.grid[i].append(SQUARE())
	for i in range(1, r + 1):
		index += 1
		line_split = f_split[index].split(" ")
		for j in range(1, c + 1):
			v = line_split[j - 1]
			s.grid[i][j].val = v
			s.grid[i][j].row = i
			s.grid[i][j].col = j
	index += 1
	nblocks = int(f_split[index])
	for i in range(0, nblocks):
		blk = set()
		index += 1
		line_split = f_split[index].split(" ")
		nsquares = int(line_split[0])
		for j in range(1, nsquares + 1):
			blk.add(chr(ord("0") + j))
		for j in range(1, nsquares + 1):
			v = line_split[j]
			v = v[1:len(v)]
			v = v[0:len(v) - 1]
			v_split = v.split(",")
			r = int(v_split[0])
			c = int(v_split[1])
			if "-" in blk:
				blk.remove(s.grid[r][c].val)
			s.grid[r][c].avail = blk
			if s.grid[r][c].val == "-":
				s.to_do.put(s.grid[r][c])

class SQUARE(object):
	def __init__(self):
		self.row = 0
		self.col = 0
		self.val = "#"
		self.avail = set()
		
	def __eq__(self, other):
		return len(self.avail) == len(other.avail)
		
	def __lt__(self, other):
		return len(self.avail) < len(other.avail)

class SOLUTION(object):
	def __init__(self):
		self.grid = []
		self.to_do = queue.PriorityQueue()

if len(sys.argv) != 2:
	print("Invalid number of arguments.")
	quit()
	
file_name = sys.argv[1]

with open(file_name, "r") as f:
	f_contents = f.read()
	
f_split = f_contents.split("\n")
p = int(f_split[0])
index = 1

for i in range(1, p + 1):
	soln = SOLUTION()
	init_solution(soln)
	index += 1
	if attempt(soln):
		print(i)
		print_soln(soln)
	else:
		print("No solution.")