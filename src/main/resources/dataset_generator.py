import json
import random
from datetime import datetime, timedelta

# Function to generate a random date between two dates
def random_date(start, end):
    return start + timedelta(seconds=random.randint(0, int((end - start).total_seconds())))

# Function to generate a single trade object
def generate_trade(trade_id):
    countries = ["USA", "Canada", "Germany", "Japan", "India", "France"]
    currencies = ["USD", "CAD", "EUR", "JPY", "INR", "ZAR"]
    counterparties = ["JP Morgan", "Goldman Sachs", "Deutsche Bank", "HSBC", "Citibank"]
    statuses = ["Pending", "Completed", "Failed"]
    ratings = ["Aaa", "Aa", "A", "Baa", "Ba"]

    trade_date = random_date(datetime(2021, 1, 1), datetime(2023, 12, 31))
    settlement_date = trade_date + timedelta(days=random.randint(1, 30))

    return {
        "tradeId": trade_id,
        "history": [random.randint(1, 30) for _ in range(10)],
        "notional": random.randint(1_000_000, 10_000_000),
        "counterparty": random.choice(counterparties),
        "currency": random.choice(currencies),
        "country": random.choice(countries),
        "changeOnYear": round(random.uniform(-50, 50), 4),
        "price": round(random.uniform(90, 110), 4),
        "bid": round(random.uniform(90, 110), 4),
        "ask": round(random.uniform(90, 110), 4),
        "bidOfferSpread": round(random.uniform(0.1, 1), 2),
        "status": random.choice(statuses),
        "isLive": random.choice([True, False]),
        "rating": random.choice(ratings),
        "tradeDate": trade_date.isoformat(),
        "settlementDate": settlement_date.isoformat(),
        "bloombergAsk": round(random.uniform(90, 110), 4),
        "bloombergbid": round(random.uniform(90, 110), 4),
        "indicativeAsk": round(random.uniform(90, 110), 4),
        "indicativebid": round(random.uniform(90, 110), 4),
        "markitAsk": round(random.uniform(90, 110), 4),
        "markitbid": round(random.uniform(90, 110), 4)
    }

# Generate a list of trades
def generate_trades(num_trades):
    return [generate_trade(trade_id) for trade_id in range(1, num_trades + 1)]

# Generate dataset
num_trades = 1000  # Adjust the number of trades
dataset = generate_trades(num_trades)

# Save to a JSON file
with open("trades.json", "w") as f:
    json.dump(dataset, f, indent=4)

print(f"Generated {num_trades} trades and saved to trades.json")