# Worker Alcyone

Worker Alcyone is a high-performance, modular Discord command processor built with Kotlin and Ktor. It is designed to scale horizontally by consuming Discord interactions from a Redis queue and responding directly to Discord's API using interaction tokens.

## 🚀 Architecture

The system operates as a distributed worker:
1. **Queueing**: A gateway (external) receives interactions from Discord and pushes them into a Redis list (`discord:interactions`).
2. **Consuming**: Multiple instances of Worker Alcyone can run concurrently, performing `BRPOP` on the Redis queue.
3. **Processing**: The worker identifies the command type and name, routing it to the appropriate modular handler.
4. **Responding**: The worker uses the Discord Interaction Token to send a reply via Ktor's HTTP Client, bypassing the need for a permanent WebSocket connection for responses.

## ✨ Key Features

- **Modular Command System**: Easily add Slash Commands, User Context Menu Commands, and Message Context Menu Commands.
- **Scalable**: Horizontal scaling support via Redis.
- **Robust Error Handling**: Built-in retry policy and Dead Letter Queue (DLQ) for failed jobs.
- **Lightweight**: Uses Ktor CIO engine for efficient asynchronous I/O.
- **Type-Safe**: Strong typing for Discord Snowflakes and Interaction Payloads.

## 🛠 Technology Stack

- **Kotlin**: 2.x
- **Ktor**: Server (Health checks/Monitoring) & Client (Discord API)
- **Redis**: Via Lettuce driver
- **Serialization**: Kotlinx Serialization (JSON)
- **Logging**: Logback

## ⚙️ Configuration

The worker can be configured using environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `REDIS_HOST` | Redis server hostname | `localhost` |
| `REDIS_PORT` | Redis server port | `6379` |
| `REDIS_PASSWORD` | Redis server password | `null` |

## 📦 Project Structure

- `adapters/discord`: Discord API client and data models.
- `domain/commands`: Modular command interfaces and implementations.
- `messaging`: Job processing, routing, and retry logic.
- `workers`: Worker supervisor and lifecycle management.

## 🆕 Adding New Commands

To add a new command, implement one of the specialized interfaces (`SlashCommand`, `UserContextCommand`, or `MessageContextCommand`):

```kotlin
object MyCommand : SlashCommand {
    override val name = "hello"
    override suspend fun execute(context: InteractionContext) {
        context.client.reply(context.payload, InteractionReplyOptions(content = "Hello World!"))
    }
}
```

Then register it in `Application.kt`:

```kotlin
CommandRegistry.register(MyCommand)
```

## 🧪 Testing

Run the test suite using Gradle:

```bash
./gradlew test
```

