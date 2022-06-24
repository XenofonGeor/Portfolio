from mrjob.job import MRJob
import time
import datetime

class PartA_2(MRJob):
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
				value = int(fields[3])
				yield (yearMonth, (value, 1))
		except:
			pass

	def combiner(self, feature, values):
		count = 0
		total = 0
		for value in values:
			count += value[1]
			total += value[0]
		yield (feature, (total,count))

	def reducer(self, feature, values):
		count = 0
		total = 0
		for value in values:
			count += value[1]
			total += value[0]
		#Average value per month
		yield (feature, total/count)

if __name__ == '__main__':
	PartA_2.run()

