from mrjob.job import MRJob

#aggregate blocks
class partC1(MRJob):

	def mapper(self, _, line):
		try:
			fields = line.split(',')
			if len(fields)==9:			
				miner = fields[2]
				size = int(fields[4])
				yield(miner,size)
		except:
			pass
	#aggregate size in the miner field
	def combiner(self,miner,value):
		yield (miner, sum(value))
	#aggregate size in the miner field
	def reducer(self,miner,value):
		yield (miner, sum(value))

if __name__ == '__main__':
    partC1.run()