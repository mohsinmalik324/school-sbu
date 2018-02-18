import statistics
import time

def convert_epoch(epoch):
	return time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime(epoch))

file = open("prices_sample.csv", "r")
dates_and_prices = []
prices = []
file_contents = file.read()
contents_split = file_contents.split("\n")

for s in contents_split:
	line_split = s.split(",")
	if len(line_split) != 2:
		continue
	converted_time = convert_epoch(int(line_split[0]))
	price = float(line_split[1])
	dates_and_prices.append((converted_time, price))
	prices.append(price)
	
min = 999999999
max = -999999999

for t in dates_and_prices:
	price = t[1]
	if price > max:
		max = price
		maxt = t
	if price < min:
		min = price
		mint = t
		
print("Min: " + mint[0] + ", " + str(round(mint[1], 2)))
print("Max: " + maxt[0] + ", " + str(round(maxt[1], 2)))
print("Mean: " + str(round(statistics.mean(prices), 2)))
print("Standard Deviation: " + str(round(statistics.pstdev(prices), 2)))

prices_len = len(prices)
if prices_len % 2 == 0:
	t1 = dates_and_prices[prices_len // 2]
	t2 = dates_and_prices[prices_len // 2 - 1]
	median = round(statistics.mean([t1[1], t2[1]]), 2)
	print("Median: " + t1[0] + ", " + t2[0] + ", " + str(median))
else:
	t = dates_and_prices[prices_len // 2]
	print("Median: " + t[0] + ", " + str(round(t[1], 2)))

file.close()