🚀 LRU Cache – Java Implementation

A clean and efficient implementation of an LRU (Least Recently Used) Cache using HashMap and a Doubly Linked List in Java.
Built using Maven, this project achieves O(1) time complexity for both get() and put() operations and follows real-world cache design principles.

✨ Features

🔹 O(1) get & put operations

🔹 Automatic eviction of Least Recently Used items

🔹 Uses HashMap + Doubly Linked List internally

🔹 Clean, modular class design

🔹 Follows production-style cache behavior

🔹 Simple to run and easy to integrate

🧠 How the LRU Cache Works
This LRU cache tracks usage order with a Doubly Linked List (DLL) and stores references in a HashMap.

------------------------------------------------------------------------------------------------------------------------------
🔮 Future Improvements

Generic Support: LRUCache<K, V>

Thread-safe Variant using ReentrantLock

Cache Metrics (hits, misses, evictions)
