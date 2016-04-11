curl   -H "X-HockeyAppToken: c42fd975a0c24b00ae1d43673f26d225"   https://rink.hockeyapp.net/api/2/apps/4c6931e7bcf5ad25dba351ee77c0db08/app_versions?include_build_urls=true > link.json

build=$(cat link.json | jq '.app_versions[0] .build_url')
build=${build#'"'}
build=${build%'"'}
curl -o logbook-debug.apk -O -L $build

echo Downloading app

aws devicefarm create-upload --project-arn arn:aws:devicefarm:us-west-2:110312683877:project:c288772b-c1e9-4aad-bc4d-1837a21c2cfb --name logbook-debug.apk.apk --type ANDROID_APP > upload.json

apparn=$(cat upload.json | jq '.upload.arn')
apparn=${apparn#'"'}
apparn=${apparn%'"'}

uploadurl=$(cat upload.json | jq '.upload.url')
uploadurl=${uploadurl#'"'}
uploadurl=${uploadurl%'"'}

echo Uploading app

curl -T logbook-debug.apk $uploadurl

echo “Waiting for 5 seconds”

sleep 5

aws devicefarm schedule-run --project-arn arn:aws:devicefarm:us-west-2:110312683877:project:c288772b-c1e9-4aad-bc4d-1837a21c2cfb --app-arn $apparn --device-pool-arn arn:aws:devicefarm:us-west-2:110312683877:devicepool:c288772b-c1e9-4aad-bc4d-1837a21c2cfb/c208533b-12f6-40dc-9177-386891ef616b --test '{"type": "BUILTIN_FUZZ"}'
echo “Tests are running”
