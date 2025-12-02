# LRU_Cache
LRU Cache – Java Implementation

A clean and efficient implementation of an LRU (Least Recently Used) Cache using a HashMap and a Doubly Linked List in Java.
This project is built using Maven, follows O(1) time complexity for both get() and put() operations, and reflects production-style LRU behavior.

📌 Features

O(1) get and put operations using HashMap + Doubly Linked List

Automatic eviction of least recently used elements when capacity is full

Clean separation of logic (Node, DLL, LRUCache)

Simple to integrate into larger systems

Maven project structure for easy build and testing

🧠 How LRU Cache Works

An LRU Cache removes the least recently accessed entry when capacity is exceeded.

This implementation uses:

HashMap <key, node> for O(1) search

Doubly Linked List to track usage order

Head = Most Recently Used

Tail = Least Recently Used

When a key is accessed:

Move node to the head

On insertion:

If full → remove tail

Insert new node at head

This keeps operations fast and predictable.

📁 Project Structure
LRU_Cache/
│
├── src/
│   └── main/java/com/lru/
│       ├── DLLNode.java
│       ├── DoublyLinkedList.java
│       └── LRUCache.java
│
├── pom.xml
└── README.md
