import pyspark

sc = pyspark.SparkContext()
#function to filter out bad lines from transactions dataset
def good_lines_transactions(line):
	try:
		fields = line.split(',')
		if len(fields)!=7:
			return False
		int(fields[4])
		int(fields[6])
		return True
	except:
		return False
#function to filter out bad lines from top 10 dataset
def good_lines_top10(line):
	try:
		fields = line.split(',')
		if len(fields)!=2:
			return False
		int(fields[1])
		return True
	except:
		return False
#read transactions dataset
transactions = sc.textFile('/data/ethereum/transactions')
#use good_lines_transactions function to filter out bad lines
transactions_good = transactions.filter(good_lines_transactions)
#map function to map to_address field as key and gas and block_timestamp fields as values from transactions
rdd1_transactions = transactions_good.map(lambda t: (t.split(',')[2], (int(t.split(',')[4]), int(t.split(',')[6]))))
#read top 10 smart contracts dataset
top10 = sc.textFile('/user/xg001/input/smart.txt')
#use good_lines_top10 function to filter out bad lines
top10_good = top10.filter(good_lines_top10)
#map function to map address field as key and value as values from top 10
rdd2_top10 = top10_good.map(lambda s: (s.split(',')[0], int(s.split(',')[1])))
#join operation for transactions and top 10 smart contracts datasets
joined_datasets = rdd1_transactions.join(rdd2_top10)
#store output at HDFS
joined_datasets.saveAsTextFile("topGasJob1")