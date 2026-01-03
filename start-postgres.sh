#!/bin/bash
set -e

DATA_DIR="/var/lib/postgresql/data"

# Initialize database if it's empty
if [ ! -d "$DATA_DIR/base" ]; then
    echo "Initializing database..."
    su - postgres -c "/usr/lib/postgresql/15/bin/initdb -D $DATA_DIR --encoding=UTF8"
    
    # Start temporary postgres to create the database
    echo "Creating database 'flossrennen'..."
    su - postgres -c "/usr/lib/postgresql/15/bin/pg_ctl -D $DATA_DIR -w start"
    su - postgres -c "/usr/lib/postgresql/15/bin/createdb flossrennen"
    su - postgres -c "/usr/lib/postgresql/15/bin/pg_ctl -D $DATA_DIR -m fast -w stop"
fi

# Ensure correct permissions
chown -R postgres:postgres $DATA_DIR
chmod 700 $DATA_DIR

# Start PostgreSQL
echo "Starting PostgreSQL..."
exec su - postgres -c "/usr/lib/postgresql/15/bin/postgres -D $DATA_DIR"
