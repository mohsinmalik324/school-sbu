import time

def convert_epoch(epoch):
	return time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime(epoch))

file = open("prices_sample.csv", "r")
to_write = open("datetimes.txt", "w")

contents = file.read()
contents_split = contents.split("\n")
converted_timestamps = []

for s in contents_split:
	line_split = s.split(",")
	if len(line_split) != 2:
		continue
	epoch = int(line_split[0])
	converted_time = convert_epoch(epoch)
	to_write.write(converted_time + "\n")

file.close()
to_write.close()