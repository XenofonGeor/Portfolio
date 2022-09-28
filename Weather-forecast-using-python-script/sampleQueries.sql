
--What will be the weather condition for each location on this Saturday at 18:00 p.m.?
SELECT address, conditions, dateNtime AS weatherDate
FROM weatherAPI_data.weather_data WHERE dateNtime = '2022-09-25 18:00:00';

--What will the maximum temperature in the next week for every location?
SELECT address, MAX(temp) AS max_temp FROM weatherAPI_data.weather_data
WHERE DATE(dateNtime) >= '2022-09-22' AND DATE(dateNtime) < '2022-09-30'
GROUP BY address;

--Which locations will have the most rainfall time in the next week?
SELECT address, COUNT(conditions) AS num_rainfall FROM weatherAPI_data.weather_data
WHERE conditions LIKE '%Rain%'
GROUP BY address
ORDER BY num_rainfall DESC

--Which locations and when they will have a moderate wind in the next week?
SELECT address, dateNtime, windSpeed FROM weatherAPI_data.weather_data
WHERE windSpeed > 15;

--Which locations will have a clear weather condition for the most time of the next five days?
SELECT address, COUNT(conditions) AS num_sunny_hours FROM weatherAPI_data.weather_data
WHERE conditions LIKE '%Clear%' AND DATE(dateNtime) > '2022-09-22'
AND DATE(dateNtime) <= '2022-09-27'
GROUP BY address
HAVING num_sunny_hours > 50
ORDER BY num_sunny_hours DESC;
