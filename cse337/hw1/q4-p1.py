import sys
import os.path

if len(sys.argv) != 2:
	print("Incorrect usage: python q4.py INPUT_FILE")
	quit()
	
file_name = sys.argv[1]
	
if not os.path.exists(file_name):
	print("File does not exist. Try again.")
	quit()

file = open(sys.argv[1], "r", encoding="utf-8")
file_contents = file.read()
contents_split = file_contents.split(" ")
dict = {}

for s in contents_split:
	newline_split = s.split("\n")
	for s2 in newline_split:
		if s2 != "":
			dict[s2] = dict.get(s2, 0) + 1
			
print(dict);