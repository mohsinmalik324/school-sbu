import sys
import math

def adjacent_init(nnodes, nqueries):
	for i in range(0, nnodes + 1):
		for j in range(0, nnodes + nqueries):
			adj[i][j] = 0
	for i in range(0, nnodes):
		adj[nnodes][i] = 1
		
def scan_edge_data(nnodes, pb):
	pb_split = pb.split(" ")
	node = int(pb_split[0])
	count = int(pb_split[1])
	adj[node - 1][node - 1] += count
	for i in range(0, count):
		val = int(pb_split[i + 2])
		adj[node - 1][val - 1] = -1
		adj[val - 1][node - 1] = -1
		adj[val - 1][val - 1] += 1
	return count
			
def solve_matrix(nnodes, nqueries):
	ncols = nnodes + nqueries
	nrows = nnodes + 1
	for currow in range(0, nnodes):
		maxrow = find_max_row(nnodes, nqueries, currow)
		if maxrow != currow:
			swap_rows(maxrow, currow, nnodes, nqueries)
		pivot = adj[currow][currow]
		if math.fabs(pivot) < .001:
			return -1
		pivot = 1 / pivot
		for i in range(currow, ncols):
			adj[currow][i] *= pivot
		eliminate(currow, nrows, ncols)
	return 0
		
def eliminate(currow, nrows, ncols):
	for i in range(0, nrows):
		if i == currow:
			continue
		factor = adj[i][currow]
		for j in range(currow, ncols):
			adj[i][j] -= factor * adj[currow][j]
			
def swap_rows(maxrow, currow, nnodes, nqueries):
	ncols = nnodes + nqueries
	for i in range(0, ncols):
		tmp = adj[currow][i]
		adj[currow][i] = adj[maxrow][i]
		adj[maxrow][i] = tmp
		
def find_max_row(nnodes, nqueries, currow):
	max = math.fabs(adj[currow][currow])
	maxrow = currow
	for i in range(currow + 1, nnodes + 1):
		tmp = math.fabs(adj[i][currow])
		if tmp > max:
			max = tmp
			maxrow = i
	return maxrow
		
if len(sys.argv) != 2:
	print("Invalid number of arguments.")
	quit()
	
max_nodes = 20
max_queries = 10

query1 = [None] * max_queries
query2 = [None] * max_queries
adj = [[0 for x in range(max_nodes + max_queries)] for y in range(max_nodes + 1)]
	
file_name = sys.argv[1]

with open(file_name, "r") as f:
	f_contents = f.read()
	
f_split = f_contents.split("\n")
p = int(f_split[0])
index = 1

for curprob in range(1, p + 1):
	line_split = f_split[index].split(" ")
	nnodes = int(line_split[1])
	nqueries = int(line_split[2])
	nedges = int(line_split[3])
	adjacent_init(nnodes, nqueries)
	ecount = 0
	while ecount < nedges:
		index = index + 1
		line_split = f_split[index].split(" ")
		scan_edge_data(nnodes, f_split[index])
		ecount += int(line_split[1])
	index = index + 1
	
	for i in range(0, nqueries):
		line_split = f_split[index].split(" ")
		a = int(line_split[1])
		b = int(line_split[2])
		query1[i] = a
		query2[i] = b
		#print("a=" + str(a) + ",b=" + str(b))
		adj[query1[i] - 1][nnodes + i] = 1
		adj[query2[i] - 1][nnodes + i] = -1
		index = index + 1
	#quit()
	i = solve_matrix(nnodes, nqueries)
	if i == 0:
		sys.stdout.write(str(curprob))
		for i in range(0, nqueries):
			dist = math.fabs(adj[query1[i] - 1][nnodes + i] - adj[query2[i] - 1][nnodes + i]);
			sys.stdout.write(" " + str(round(dist, 3)))
		sys.stdout.write("\n")
		sys.stdout.flush()
	else:
		print("error return " + str(i) + " from solve_matrix problem " + str(curprob))