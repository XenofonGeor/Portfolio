import urllib.request
import json
import mysql.connector
from datetime import date, datetime, timedelta

# This is the base of the weather query URL
BaseURL = 'https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/'

#UnitGroup specifies the units of the output metric
UnitGroup='metric'

#Set type of query to FORECAST
QueryType='FORECAST'

#Integer indicating the maximum number of days of forecast to retrieve.
forecastDays = '7'

#1=hourly data
AggregateHours='1'

#Loop to get data for three locations
for i in range (3):
    ApiKey='D98YTR55B6RC5NEJT4URG8GQH'
    print('Enter locations:')
    Locations= input()
    # Set up the specific parameters based on the type of query
    if QueryType == 'FORECAST':
        print(' - Fetching forecast data')
        QueryParams = 'forecast?aggregateHours=' + AggregateHours + '&forecastDays=' + forecastDays + '&unitGroup=' + UnitGroup + '&shortColumnNames=true'

    Locations='&locations='+Locations

    ApiKey='&key='+ApiKey

    # Build the whole query
    URL = BaseURL + QueryParams + Locations + ApiKey+"&contentType=json"

    print(' -Query URL running: ', URL)
    print()

    #Fetch URL
    response = urllib.request.urlopen(URL)
    #Read the content
    data = response.read()
    #Parse the json string and convert it
    weatherData = json.loads(data.decode('utf-8'))

    print( "Connecting to MySQL database...")

    #connect to the database. Enter your host, username and password
    cnx = mysql.connector.connect(host='127.0.0.1',
        user='root',
        password='*******',
        database='weatherAPI_data')

    cursor = cnx.cursor()

    # Create an insert statement for inserting rows of data
    insert_weather_data = ("INSERT INTO `weatherAPI_data`.`weather_data`"
                    "(`address`,`latitude`,`longitude`,`dateNtime`,`temp`,`precip`,`windSpeed`,`windDir`,`windGust`,`pressure`,`conditions`)"
                    "VALUES (%(address)s, %(latitude)s, %(longitude)s, %(dateNtime)s, %(temp)s, %(precip)s, %(windSpeed)s, %(windDir)s, %(windGust)s, %(pressure)s, %(conditions)s)")

    # Iterate through the locations
    locations=weatherData["locations"]
    for locationId in locations:
        location=locations[locationId]
        # Iterate through the time periods in the weather data
        for value in location["values"]:
            data_wx = {
            'address': location["address"],
            'latitude': location["latitude"],
            'longitude': location["longitude"],
            'dateNtime': datetime.utcfromtimestamp(value["datetime"]/1000.),
            'temp': value["temp"],
            'precip': value["precip"],
            'windSpeed': value["wspd"],
            'windDir': value["wdir"],
            'windGust': value["wgust"],
            'pressure': value["sealevelpressure"],
            'conditions': value["conditions"]
            }
            #Execute the query
            cursor.execute(insert_weather_data, data_wx)
            #Send a COMMIT statement to the MySQL server, committing the current transaction
            cnx.commit()

    cursor.close()
    cnx.close()
    print( "Database connection closed")

    print( "Completed")
