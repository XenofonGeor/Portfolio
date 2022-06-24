from mrjob.job import MRJob
#aggregate top 10 values
class partC2(MRJob):
	
	def mapper(self, _, line):
		try:
			if len(line.split('\t'))==2:#reads partC1 output file
				fields = line.split('\t')
				miner = fields[0]
				miner = miner[1:-1]#remove double quotes
				size = int(fields[1])
				yield (None,(miner,size))
		except:
			pass
	#sort values and show top ten most active miners
	def reducer(self,_, values):
		sortedValues = sorted(values, reverse = True, key = lambda tup:tup[1])#sort values in descending order
		counter = 0
		for value in sortedValues:
			yield(counter, ("{} - {}".format(value[0], value[1])))
			counter += 1
			if counter >= 10:
				break

if __name__ == '__main__':
	partC2.run()