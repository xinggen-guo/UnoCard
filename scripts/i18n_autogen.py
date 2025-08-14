import json
import os

# Configuration
I18N_DIR = "shared/src/commonMain/composeResources/i18n"
OUTPUT_KT = "shared/src/commonMain/kotlin/i18n/I18nKeys.kt"

# Collect all unique keys from language files
all_keys = set()
for file in os.listdir(I18N_DIR):
    if file.endswith(".json"):
        with open(os.path.join(I18N_DIR, file), "r", encoding="utf-8") as f:
            data = json.load(f)
            all_keys.update(data.keys())

# Generate Kotlin enum code
lines = [
    "package i18n",
    "",
    "/**",
    " * Automatically generated I18n string keys.",
    " * Do not modify manually. Use scripts/i18n_autogen.py instead.",
    " */",
    "enum class I18nKeys(val key: String) {"
]

for k in sorted(all_keys):
    lines.append(f"    {k}(\"{k}\"),")

lines.append("")
lines.append("    ;")
lines.append("")
lines.append("    override fun toString(): String = key")
lines.append("}")

# Write to file
with open(OUTPUT_KT, "w", encoding="utf-8") as f:
    f.write("\n".join(lines))

print("✅ I18nKeys.kt has been regenerated.")