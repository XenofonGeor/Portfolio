from mrjob.job import MRJob
import time
import datetime

class PartA(MRJob):
	def mapper(self, _, line):
		try:
			fields = line.split(",")
			if (len(fields)==7):
				time_epoch = int(fields[6])
				#returns month in appropriate format
				month = time.strftime("%m",time.gmtime(time_epoch))
				#returns year in appropriate format
				year = time.strftime("%Y",time.gmtime(time_epoch))
				yearMonth = (year,month)
				yield (yearMonth, 1)
		except:
			pass
	def combiner(self, month, count):
		yield (month, sum(count))

	def reducer(self, month, count):
		yield (month, sum(count))

if __name__ == '__main__':
	PartA.run()