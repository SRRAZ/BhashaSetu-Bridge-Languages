# English Pronunciation Assessment Models

This directory contains machine learning models and resources used for offline English pronunciation assessment.

## Models

1. **YAMNet** (`yamnet.tflite`)
   - Audio event classification model
   - Used to detect and classify speech and audio events
   - Input: 16kHz mono audio with 1 second duration
   - Output: 521 class probabilities for audio events

2. **English Pronunciation Guide** (`english_common_words.csv`)
   - Reference data for common English words and phrases
   - Contains phonetic transcriptions for proper pronunciation assessment
   - Used to match recorded audio against correct pronunciations

## Files

- `yamnet.tflite` - Pre-trained YAMNet model for audio classification
- `yamnet_class_map.csv` - Mapping of class indices to human-readable labels for YAMNet
- `english_common_words.csv` - List of common English words with phonetic transcriptions
- `model_info.json` - Metadata for models and configuration

## Usage

The TensorFlowLiteAudioAnalyzer class uses these models to:
1. Capture audio from the user
2. Process the audio through YAMNet to detect speech
3. Compare speech patterns against reference pronunciations
4. Provide feedback on pronunciation accuracy

The PronunciationModelManager class handles loading and managing these models.