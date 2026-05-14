# Siri-Dhanya Hub

A buildable Android starter app based on the PDF specification.

Includes:
- Onboarding role selection
- Simulated mandi price dashboard with 7-day high/low and trend arrows
- Recipe lab
- Offline favourites stored with Room
- Health benefits cards
- FPO connect screen
- Local AI-style recipe suggestion logic
- Search across recipes/prices/health content

## Build in Android Studio
1. Open the `SiriDhanyaHubApp` folder.
2. Let Gradle sync finish.
3. Run on an Android 7.0+ device/emulator.

## Notes
- The PDF asked for GenAI integration, offline recipe save, and 7-day mandi range support. This project implements all three in a demo-friendly way using local logic and Room persistence. fileciteturn0file0
- To connect a real Gemini/OpenAI endpoint later, replace the `recommendRecipe()` logic in `AppRepository`.
