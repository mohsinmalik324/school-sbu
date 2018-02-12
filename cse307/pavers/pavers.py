import sys
import math

def comp_tiles():
	F[0] = 1
	F[1] = 2
	F[2] = 11
	F1[0] = 0
	F1[1] = 2
	F1[2] = 16
	F2[0] = 0
	F2[1] = 1
	F2[2] = 8
	F3[0] = 0
	F3[1] = 0
	F3[2] = 4
	G[0] = 0
	G[1] = 0
	G[2] = 2
	G1[0] = G1[1] = 0
	G1[2] = 1
	G2[0] = G2[1] = 0
	G2[2] = 1
	G3[0] = G3[1] = 0
	G3[2] = 1
	for n in range(2, 400):
		F[n + 1] = 2 * F[n] + 7 * F[n - 1] + 4 * G[n]
		F1[n + 1] = 2 * F1[n] + 2 * F[n] + 7 * F1[n - 1] + 8 * F[n - 1] + 4 * G1[n] + 2 * G[n]
		F2[n + 1] = 2 * F2[n] + F[n] + 7 * F2[n - 1] + 4 * F[n - 1] + 4 * G2[n] + 2 * G[n]
		F3[n + 1] = 2 * F3[n] + 7 * F3[n - 1] + 4 * F[n - 1] + 4 * G3[n] + 2 * G[n]
		test = 2 * (n + 1) * F[n + 1]
		test1 = F1[n + 1] + 2 * F2[n + 1] + 3 * F3[n + 1]
		if math.fabs(test - test1) > .0000001 * test:
			print("mismatch " + str(n + 1) + ": " + str(test) + " != " + str(test1) + "\n")
		G[n + 1] = 2 * F[n - 1] + G[n]
		G1[n + 1] = 2 * F1[n - 1] + F[n - 1] + G1[n]
		G2[n + 1] = 2 * F2[n - 1] + F[n - 1] + G2[n] + G[n]
		G3[n + 1] = 2 * F3[n - 1] + F[n - 1] + G3[n]

if len(sys.argv) != 2:
	print("Invalid number of arguments.")
	quit()
	
F = [None] * 401
F1 = [None] * 401
F2 = [None] * 401
F3 = [None] * 401
G = [None] * 401
G1 = [None] * 401
G2 = [None] * 401
G3 = [None] * 401

file_name = sys.argv[1]

with open(file_name, "r") as f:
	f_contents = f.read()
	
f_split = f_contents.split("\n")
p = int(f_split[0])

comp_tiles()

for i in range(1, p + 1):
	row_split = f_split[i].split(" ")
	k = int(row_split[0])
	n = int(row_split[1])
	if n == 1:
		print(str(i) + " 2 2 1 0")
	elif n < 2 or n > 400:
		print("array width " + str(n) + " not in range 2 .. 400 problem " + str(i))
	else:
		print(str(i) + " " + str(F[n]) + " " + str(F1[n]) + " " + str(F2[n]) + " " + str(F3[n]))