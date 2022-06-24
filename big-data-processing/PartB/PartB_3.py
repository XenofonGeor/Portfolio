from mrjob.job import MRJob

class partB3(MRJob):
	
	def mapper(self, _, line):
		try:
			if len(line.split('\t'))==2:#mapper reads the output from job2
				fields = line.split('\t')
				address = fields[0]
				address = address[1:-1]#remove double quotes("")
				count = int(fields[1])
				yield (None,(address,count))
		except:
			pass
	#sort the top 10 values
	def combiner(self,_, values):
		sortedValues = sorted(values, reverse = True, key = lambda tup:tup[1])
		counter = 0
		for value in sortedValues:
			yield("top", value)
			counter += 1
			if counter >= 10:
				break
	#sort the top 10 values and yield them
	def reducer(self,_, values):
		sortedValues = sorted(values, reverse = True, key = lambda tup:tup[1])
		counter = 0
		for value in sortedValues:
			yield(counter, ("{} - {}".format(value[0], value[1])))
			counter += 1
			if counter >= 10:
				break

if __name__ == '__main__':
	partB3.run()