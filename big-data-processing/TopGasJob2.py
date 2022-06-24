from mrjob.job import MRJob
import time
import datetime

class GasTop(MRJob):
	#mapper yields to_address, formatted block_timestamp as key and gas field and 1 as values
	def mapper(self, _, line):
		try:
			fields = line.split(",")
			to_address = fields[0]
			time_epoch = int(fields[2][:-1])#remove extra brackets
			gas = int(fields[1][3:])
			month = time.strftime("%m",time.gmtime(time_epoch))
			year = time.strftime("%Y",time.gmtime(time_epoch))
			yearMonth = (year,month)
			yield ((to_address, yearMonth), (gas, 1))
		except:
			pass
	#combiner yields to_address, formatted block_timestamp as key and total gas, counter as values
	def combiner(self, feature, values):
		count = 0
		total = 0
		for value in values:
			count += value[1]
			total += value[0]
		yield (feature, (total,count))
	#reducer yields to_address, formatted block_timestamp as key and average gas as values
	def reducer(self, feature, values):
		count = 0
		total = 0
		for value in values:
			count += value[1]
			total += value[0]
		yield (feature, total/count)

if __name__ == '__main__':
	GasTop.run()