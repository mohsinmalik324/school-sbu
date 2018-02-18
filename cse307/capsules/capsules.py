import sys

class square(object):
	def __init__(self):
		self.val = ""
		self.row = 0
		self.col = 0
		selt.avail = set()

class solution(object):
	def __init__(self):
		self.grid = []

if len(sys.argv) != 2:
	print("Invalid number of arguments.")
	quit()
	
file_name = sys.argv[1]

with open(file_name, "r") as f:
	f_contents = f.read()
	
f_split = f_contents.split("\n")
p = int(f_split[0])

for i in range(1, p + 1):
	