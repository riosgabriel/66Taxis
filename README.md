66Taxis
-----------------------

API
-----------------------
Payloads examples:

POST /taxi
{
  "taxi": {
    "x": 4,
    "y": 5
  }
}

POST /passenger
{
  "passenger": {
    "location": {
      "x": 7,
      "y": 7
    },
    "destination": {
      "x": 5,
      "y": 12
    }
  }
}