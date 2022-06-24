from mrjob.job import MRJob
import time
import datetime

class GasPrice(MRJob):
	#mapper yields formatted block_timestamp as key and gasPrice field and 1 as values
	def mapper(self, _, line):
		try:
			fields = line.split(",")
			if (len(fields)==7): #read from transactions dataset
				gasPrice = int(fields[5])
				time_epoch = int(fields[6])
				month = time.strftime("%m",time.gmtime(time_epoch))
				year = time.strftime("%Y",time.gmtime(time_epoch))
				yearMonth = (year,month)
				yield (yearMonth, (gasPrice, 1))
		except:
			pass
	#combiner yields formatted block_timestamp as key and total gasPrice and counter as values
	def combiner(self, feature, values):
		count = 0
		total = 0
		for value in values:
			count += value[1]
			total += value[0]
		yield (feature, (total,count))
	#reducer yields formatted block_timestamp as key and average gasPrice as values
	def reducer(self, feature, values):
		count = 0
		total = 0
		for value in values:
			count += value[1]
			total += value[0]
		yield (feature, total/count)

if __name__ == '__main__':
	GasPrice.run()