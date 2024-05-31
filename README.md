# Sprawozdanie - Apache Kafka - NYC Yellow Taxi 
Joanna Neubauer 148111

## 1. ściągnij/wrzuć wsyzystkie pliki na maszynę

## 2. dodaj uprawnienia skryptom 
```
chmod +x *.sh
```

## 3. Run the setup scripts
### Assign enviromental variables
```
./environ.sh
```

### Create kafka topics
```
./create-topics.sh
```

### Turn on listining terminal
```
./kafka-reciver.sh
```

### Turn on sender terminal
```
./kafka-sender.sh
```