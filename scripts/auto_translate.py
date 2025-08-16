#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Auto Translate Script
---------------------
Reads English i18n json and generates multi-language translations using LibreTranslate API.
Supports incremental updates and customizable language configuration.

Dependencies:
  pip install requests

Author: xinggen.guo
Date: 2025-08-14
"""

import json
import os
import time
import openai

# ==== CONFIG ====
I18N_DIR = "shared/src/commonMain/composeResources/i18n"
LANGUAGES_FILE = os.path.join(I18N_DIR, "languages.json")
SOURCE_LANG = "en"
openai.api_key = os.getenv("OPENAI_API_KEY")  # Set your API key in environment variable

# ==== UTIL ====
def load_json(path):
    if os.path.exists(path):
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    return {}

def save_json(path, data):
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)

def chatgpt_translate(text, target_lang):
    prompt = f'Translate the following text to {target_lang}:\n"{text}"\nOnly return the translated result without quotes.'
    try:
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",  # or gpt-4
            messages=[{"role": "user", "content": prompt}],
            temperature=0.3,
        )
        return response.choices[0].message.content.strip()
    except Exception as e:
        print(f"❌ Translation failed: {e}")
        return text  # fallback to original text

# ==== MAIN ====
def main():
    # Load languages config
    langs_config = load_json(LANGUAGES_FILE)
    supported = langs_config.get("supported", [])
    source_data = load_json(os.path.join(I18N_DIR, f"{SOURCE_LANG}.json"))

    for lang in supported:
        code = lang["code"]
        if code == SOURCE_LANG:
            continue  # Skip source lang

        print(f"\n🌍 Translating to '{code}'...")
        target_file = os.path.join(I18N_DIR, f"{code}.json")
        existing = load_json(target_file)

        updated = False
        translated = existing.copy()

        for key, text in source_data.items():
            if key in existing:
                continue  # Skip already translated
            print(f" ↳ Translating key: '{key}'...")
            result = chatgpt_translate(text, lang["name"])
            translated[key] = result
            updated = True
            time.sleep(1.5)  # avoid rate limit

        if updated:
            save_json(target_file, translated)
            print(f"✅ Saved: {target_file}")
        else:
            print("⚠️ No updates needed.")

if __name__ == "__main__":
    main()