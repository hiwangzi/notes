```
redis-cli --scan --pattern "your_pattern" | awk '{print $1}' | sed "s/^/'/;s/$/'/" | xargs redis-cli del
```
