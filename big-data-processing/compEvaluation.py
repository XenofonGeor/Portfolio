import pyspark

sc = pyspark.SparkContext()
#function to filter out bad lines from transactions dataset
def good_lines_transactions(line):
	try:
		fields = line.split(',')
		if len(fields)!=7:
			return False
		int(fields[3])
		return True
	except:
		return False
#function to filter out bad lines from contracts dataset
def good_lines_contracts(line):
	try:
		fields = line.split(',')
		if len(fields)!=5:
			return False
		return True
	except:
		return False
#read transactions dataset
transactions = sc.textFile('/data/ethereum/transactions')
#use good_lines_transactions function to filter out bad lines
transactions_good = transactions.filter(good_lines_transactions)
#map function to map to_address field as key and value as values from transactions
rdd1_transactions = transactions_good.map(lambda t: (t.split(',')[2], int(t.split(',')[3])))
#reduceByKey function to aggregate value
outJob1 = rdd1_transactions.reduceByKey(lambda a,b: (a+b)).sortByKey()
#read contracts dataset
contracts = sc.textFile('/data/ethereum/contracts')
#use good_lines_contracts function to filter out bad lines
contracts_good = contracts.filter(good_lines_contracts)
#map function to map address field as key and block_number as values from contracts
rdd2_contracts = contracts_good.map(lambda c: (c.split(',')[0], c.split(',')[3]))
#join operation for transactions and contracts datasets
joined_datasets = outJob1.join(rdd2_contracts)
#filter top 10 smart contracts from the joined_datasets
topTen = joined_datasets.takeOrdered(10, key = lambda x:-x[1][0])

counter = 0

for value in topTen:

	counter += 1

	print(counter, "{} {}".format(value[0], value[1][0]))