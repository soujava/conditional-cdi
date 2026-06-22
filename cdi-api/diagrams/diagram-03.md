graph TD
A[Untrusted User Input] --> B[Input Guardrail: Validate, Sanitize, Strip PII]
B -->|Cleaned Prompt| C((LLM Generation))
C -->|Unstructured Text / JSON| D[Output Guardrail: Parse & Validate against Schema]
D -->|Success| E[Return Strict DTO to System]
D -.->|Validation Failed / Hallucination| F[Fallback Route: Safe Default]

    style A fill:#F8F7F7,stroke:#1D5183,stroke-width:2px,color:#1D5183
    style B fill:#F8F7F7,stroke:#019DDC,stroke-width:2px,color:#1D5183
    style C fill:#1D5183,stroke:#019DDC,stroke-width:2px,color:#F8F7F7
    style D fill:#F8F7F7,stroke:#019DDC,stroke-width:2px,color:#1D5183
    style E fill:#F8F7F7,stroke:#1D5183,stroke-width:2px,color:#1D5183
    style F fill:#F8F7F7,stroke:#1D5183,stroke-width:2px,stroke-dasharray: 5 5,color:#1D5183