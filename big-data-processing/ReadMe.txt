
# Ethereum Analysis
The goal of this project is to apply Big Data Processing techniques to analyse the full set of transactions which have occurred on the Ethereum network; from the first transactions in August 2015 till June 2019. I have created several Map/Reduce and Spark programs to perform multiple types of computation.


The dataset I have used is a subset of the data available on BigQuery. The blocks, contracts and transactions tables have been pulled down and been stripped of unneeded fields to reduce their size. Also, a set of scams has been downloaded, both active and inactive, run on the Ethereum network via etherscamDB.


Task

Write a set of Map/Reduce (or Spark) jobs that process the given input and generate the data required to answer the following questions:

PART A. TIME ANALYSIS
Create a bar plot showing the number of transactions occurring every month between the start and end of the dataset.

Create a bar plot showing the average value of transaction in each month between the start and end of the dataset.

PART B. TOP TEN MOST POPULAR SERVICES
Evaluate the top 10 smart contracts by total Ether received. An outline of the subtasks required to extract this information is provided below, focusing on a MRJob based approach.

JOB 1 - INITIAL AGGREGATION
To workout which services are the most popular, you will first have to aggregate transactions to see how much each address within the user space has been involved in. You will want to aggregate value for addresses in the to_address field.

JOB 2 - JOINING TRANSACTIONS/CONTRACTS AND FILTERING
Once you have obtained this aggregate of the transactions, the next step is to perform a repartition join between this aggregate and contracts (example here). You will want to join the to_address field from the output of Job 1 with the address field of contracts

Secondly, in the reducer, if the address for a given aggregate from Job 1 was not present within contracts this should be filtered out as it is a user address and not a smart contract.

JOB 3 - TOP TEN
Finally, the third job will take as input the now filtered address aggregates and sort these via a top ten reducer, utilising what you have learned from lab 4.

PART C. TOP TEN MOST ACTIVE MINERS
Evaluate the top 10 miners by the size of the blocks mined. You will first have to aggregate blocks to see how much each miner has been involved in. You will want to aggregate size for addresses in the miner field. You can add each value from the reducer to a list and then sort the list to obtain the most active miners.

PART D. DATA EXPLORATION 
The final part of the project is about exploring the data and performing some analysis of my choosing. These tasks may be completed in either MRJob or Spark.

SCAM ANALYSIS
Popular Scams: Utilising the provided scam dataset, what is the most lucrative form of scam? How does this change throughout time, and does this correlate with certain known scams going offline/inactive?

MISCELLANEOUS ANALYSIS

Gas Guzzlers: For any transaction on Ethereum a user must supply gas. How has gas price changed over time? Have contracts become more complicated, requiring more gas, or less so? How does this correlate with your results seen within Part B.

Comparative Evaluation: Reimplement Part B in Spark (if your original was MRJob, or vice versa). How does it run in comparison? Keep in mind that to get representative results you will have to run the job multiple times, and report median/average results. Can you explain the reason for these results? What framework seems more appropriate for this task?


