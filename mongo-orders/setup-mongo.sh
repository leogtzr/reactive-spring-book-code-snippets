#!/bin/bash
readonly error_running_mongo=80

set -x
if ! mongo --eval "rs.initiate({_id: 'rs0', members:[{_id: 0, host: '127.0.0.1:27017'}]});"; then
	echo "Error setting up mongo with a replica set" >&2
	exit ${error_running_mongo}
fi
exit 0
