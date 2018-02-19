import math

file = open("prices_sample.csv", "r")
file_contents = file.read()
contents_split = file_contents.split("\n")
sumXY = sumX = sumY = sumX2 = sumY2 = n = 0
for s in contents_split:
	line_split = s.split(",")
	if len(line_split) != 2:
		continue
	epoch = int(line_split[0])
	price = float(line_split[1])
	sumXY += epoch * price
	sumX += epoch
	sumY += price
	sumX2 += epoch ** 2
	sumY2 += price ** 2
	n += 1
	
top = sumXY - ((sumX * sumY) / n)
bottom = (sumX2 - ((sumX ** 2) / n)) * (sumY2 - ((sumY ** 2) / n))
bottom = math.sqrt(bottom)
result = top / bottom
print(result)

file.close()