from mrjob.job import MRJob

#aggregate transactions dataset
class partB1(MRJob):

	def mapper(self, _, line):
		try:
			fields = line.split(',')
			toAddress = fields[2]
			value = int(fields[3])
			#yield only not null value
			if value == 0:
				pass
			else:
				yield(toAddress,value)
		except:
			pass

	def combiner(self,toAddress,value):
		yield (toAddress, sum(value))

	def reducer(self,toAddress,value):
		yield (toAddress, sum(value))#this outputs toptenOutput1.tsv

if __name__ == '__main__':
    partB1.run()