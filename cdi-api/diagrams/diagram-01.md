
```mermaid
graph LR
    subgraph Deterministic Environment [Your Enterprise Architecture]
        Core[Core Domain Logic] -->|Strict Java Interfaces & DTOs| ACL[Anti-Corruption Layer]
    end
    
    subgraph Probabilistic Environment [External AI]
        ACL -.->|JSON / Fragile Prompts| LLM[(Large Language Model)]
    end
    
    style Core fill:#F8F7F7,stroke:#1D5183,stroke-width:2px,color:#1D5183
    style ACL fill:#F8F7F7,stroke:#019DDC,stroke-width:2px,stroke-dasharray: 5 5,color:#1D5183
    style LLM fill:#1D5183,stroke:#019DDC,stroke-width:2px,color:#F8F7F7
```