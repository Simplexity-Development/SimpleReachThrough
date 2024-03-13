# Simple Reach Through

Basic plugin that allows reaching through item frames, signs, and paintings when they are placed on a container
You can avoid reaching through these by crouching or toggling the functionality off

There are configuration options for if empty item frames and unwaxed signs should be reached through or not


| Command        | Permission          | Description                     |
|----------------|---------------------|---------------------------------|
| `/srreload`    | reachthrough.reload | Reloads the plugin              |
| `/reachtoggle` | reachthrough.toggle | Toggles reach-through abilities |

| **Permissions**       | Default | Description                                    |
|-----------------------|---------|------------------------------------------------|
| `reachthrough.reload` | op      | Allows the user to reload the plugin           |
| `reachthrough`        | true    | Allows the user to use the plugin              |
| `reachthrough.toggle` | op      | Allows the user to toggle reachthrough ability |

## Default Config
```yml
#right click pass through
item-frames:
  reach-through: true
  reach-through-empty: false
signs:
  reach-through: true
  reach-through-unwaxed: false
paintings:
  reach-through: true
```