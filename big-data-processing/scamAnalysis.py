import pyspark
import time

sc = pyspark.SparkContext()
#function to filter out bad lines from transactions dataset
def good_lines_transactions(line):
	try:
		fields = line.split(',')
		if len(fields)!=7:
			return False
		int(fields[6])
		int(fields[3])
		return True
	except:
		return False
#read transactions dataset
transactions = sc.textFile('/data/ethereum/transactions')
#use good_lines_transactions function to filter out bad lines
transactions_good = transactions.filter(good_lines_transactions)
#map function to map to_address field as key and block_timestamp field and value as values from transactions
rdd1_transactions = transactions_good.map(lambda t: (t.split(',')[2], (int(t.split(',')[6]), int(t.split(',')[3]))))
#read scams dataset
scams = sc.textFile('/user/xg001/input/convert.csv')
#map function to map the address and category fields from scams dataset
rdd2_scams = scams.map(lambda s: (s.split(',')[0], s.split(',')[1]))
#join operation for transactions and scams datasets
joined_datasets = rdd1_transactions.join(rdd2_scams)
#map category as key and value as values
lucrativeScams = joined_datasets.map(lambda a: (a[1][1], a[1][0][1]))
#reduceByKey function to aggregate value
sum_lucrativeScams = lucrativeScams.reduceByKey(lambda a,b: (a+b)).sortByKey()
#save the result with category as key and aggregated value as values
sum_lucrativeScams.saveAsTextFile('lucrativeScams')
#map category as key and block_timestamp and value as values
timeseries = joined_datasets.map(lambda b: ((b[1][1], time.strftime("%m-%Y",time.gmtime(b[1][0][0]))), b[1][0][1]))
#reduceByKey to aggregate values
sum_timeseries = timeseries.reduceByKey(lambda a,b: (a+b)).sortByKey()
#save the result with category, and timestamp along with the aggregated values.
sum_timeseries.saveAsTextFile('scamTimeseries')