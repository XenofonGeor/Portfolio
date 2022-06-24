from mrjob.job import MRJob

class partB2(MRJob):
	
	def mapper(self, _, line):
		try:
			if len(line.split('\t'))==2:#if it reads from the output of job1
				fields = line.split('\t')
				toAddress = fields[0]
				toAddress = toAddress[1:-1]#remove double quotes ("")
				tValue = int(fields[1])
				yield (toAddress,(tValue,1))#identifier is 1

			elif len(line.split(','))==5:#if it reads from the contracts dataset
				fields = line.split(',')
				address = fields[0]
				cValue = int(fields[3])
				yield (address,(cValue,2))#identifier is 2
		except:
			pass

	def reducer(self, addresses, values):
		blockNumber = 0
		counts = 0
		for i in values:
			if i[1]==1:#if it reads from the identifier 1
				counts = i[0]#get value
			elif i[1]==2:#if it reads from the identifier 2
				blockNumber = i[0]#get value

		if blockNumber > 0 and counts > 0:#valid contract should be bigger than 0
			yield(addresses,counts)

if __name__ == '__main__':
	partB2.run()