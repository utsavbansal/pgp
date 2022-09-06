Document to know how to run the code:
Make .jar file by using the code present in git directory.

Docker commands to run the code:
Step 1: Go to directory/folder where "docker-compose.yml" is present.
Step 2: use command: docker compose up
the above command will automatically download the dependencies that are required to run the jar file.

Note1: In line 7 of "docker-compose.yml" please change the path according to your directory structure i.e "/home/farmerzone/SAIAfarming:/opt" will be like "/path_acc_to_your_directory_structure_where_the_application.properties_file_is_present:opt".[for Docker compose "application.properties" file is provided in seperate folder/directory i.e alongside "server" and "SAIAFarm" repositories.]

Note2: In line 22 of "docker-compose.yml" please change the path where you want to store data of mysql[this is the path where all the metadata and your data will be stored(this is important because if you dont use this path then when you will close the container by using "docker compose down" command then you will lost all the data you had stores by using API's)]. This path will be like this: /home/farmerzone/Desktop/Docker_compose_db:/var/lib/mysql  to this /path_where_you_want_to_store_meta_data_&_data/Docker_compose_db:/var/lib/mysql.

Note3: To use the docker in IIT proxy network or other proxy network please first set the proxy. No need to set the proxy if you are using your personal internet connection.
For IIT Network follow the Steps mentioned below:
 
Step1: sudo systemctl edit docker.service


Step2:paste this:-----------

[Service]
Environment="HTTP_PROXY=10.8.0.1:8080"
Environment="HTTP_PROXY=10.8.0.1:8080"


NOW restart docker

Step3: sudo systemctl restart docker.service

to check docker proxy use ::::::::::sudo docker info | grep -i proxy


Note4: To download the "openjdk" i.e command present in Dockerfile have only few hundred attempts per day per user.


API path and how to use it on postman or other platforms:

1 to create user: POST request: localhost:9099/user/registration
please save the user_id after creating user from above API. It will be user in all other API.
2. for login: POST request: localhost:9099/login
3. To insert the details of the farmer: POST Request: localhost:9099/post_farmerdata

Here body will be like mentioned below [Select radio button "raw" and select  "JSON(application/json)" format]:
{
    "type": "Feature",
    "properties": {
        "user_id":"anch3072",
        "head_name":"anchal kaundal",
        "gender": "Male",
        "son_wife_daughter":"xyz1",
        "literacy":"10",
        "village":"kamand",
        "address_landmark":"near south campus treat"

    },
    "geometry": {
        "type": "Polygon",
        "coordinates": [
            [
                [18.5016632080, 54.5186902372369],
                [18.5009765625, 54.4488835908718],
                [18.4467315673, 54.4101394181849],
                [18.4748840332, 54.3961505667008],
                [18.5270690917, 54.5194873388633],
                [18.5016632080, 54.5186902372369]
            ]
        ]
    }}

4. Get data of single farmer: GET request: localhost:9099/farmerdata?farmer_id=1
Please provide farmer_id in API link accordingly.

5. Get all farmers personal data(admin use only): GET request: localhost:9099/get_farmersdata

6. To insert farm detail of specific farmer: POST request: localhost:9099/post_farmdata

Here body will be like mentioned below [Select radio button "raw" and select  "JSON(application/json)" format]:
[{
    "type": "Feature",
    "properties": {
        "userId":"anch3072",
        "farmer_id":"2",
        "farmsizecategory": "Male",
        "farm_name":"neri1",
        "farm_type":"own",
        "farm_size":"2",
        "village":"kamand",
        "address_landmark":"neri"

    },
    "geometry": {
        "type": "Polygon",
        "coordinates": [
            [
                [18.5016632080, 54.5186902372369],
                [18.5009765625, 54.4488835908718],
                [18.4467315673, 54.4101394181849],
                [18.4748840332, 54.3961505667008],
                [18.5270690917, 54.5194873388633],
                [18.5016632080, 54.5186902372369]
            ]
        ]
    }
    }
    ]



7. Get data of single farm: : GET request: localhost:9099/farmdata?farm_id=1
Please provide farm_id in API link accordingly.

8. Get all farms data(admin use only): GET request: localhost:9099/get_farmsdata

9. To insert parcel detail of specific farm: POST request:localhost:9099/post_parceldata

Here body will be like mentioned below [Select radio button "raw" and select  "JSON(application/json)" format]:

[{
    "type": "Feature",
    "properties": {
        "userId":"anch3072",
        "farmId":"1",
        "area":"biga",
        "category": "croping",
        "application":"using resource",
        "applicationEntityId":"100",
        "belongsTo":"farmid1",
        "hasAgriCrop":"wheat",
        "hasAgriSoil":"black",
        "lastPlantedAt":"2022-01-10",
        "waterStressmean":"dont know"

    },
    "geometry": {
        "type": "Polygon",
        "coordinates": [
            [
                [18.5016632080, 54.5186902372369],
                [18.5009765625, 54.4488835908718],
                [18.4467315673, 54.4101394181849],
                [18.4748840332, 54.3961505667008],
                [18.5270690917, 54.5194873388633],
                [18.5016632080, 54.5186902372369]
            ]
        ]
    }}]

10. Get data of parcel farm: : GET request:: localhost:9099/parceldata?parcelId=1
Please provide parcelId in API link accordingly.

11. Get all parcel data(admin use only): GET request: localhost:9099/get_parcelsdata

