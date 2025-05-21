# Test Devices for Performance Testing

This document details the recommended test devices for comprehensive performance testing of the English-Hindi Learning App.

## Device Tiers

### Low-End Devices
These devices represent users with older or budget smartphones:

1. **Samsung Galaxy A10**
   - Android 9.0 (API 28)
   - 2GB RAM
   - Exynos 7884 processor
   - 720 x 1520 resolution

2. **Xiaomi Redmi 7A**
   - Android 9.0 (API 28)
   - 2GB RAM
   - Snapdragon 439 processor
   - 720 x 1440 resolution

3. **Nokia 2.3**
   - Android 10.0 (API 29)
   - 2GB RAM
   - MediaTek MT6761 Helio A22 processor
   - 720 x 1520 resolution

### Mid-Range Devices
These devices represent the majority of the target user base:

1. **Samsung Galaxy M31**
   - Android 11.0 (API 30)
   - 6GB RAM
   - Exynos 9611 processor
   - 1080 x 2340 resolution

2. **Xiaomi Redmi Note 10**
   - Android 11.0 (API 30)
   - 4GB RAM
   - Snapdragon 678 processor
   - 1080 x 2400 resolution

3. **Realme 7**
   - Android 10.0 (API 29)
   - 6GB RAM
   - MediaTek Helio G95 processor
   - 1080 x 2400 resolution

### High-End Devices
These devices represent premium users and help identify the app's peak performance:

1. **Samsung Galaxy S21**
   - Android 12.0 (API 31)
   - 8GB RAM
   - Exynos 2100 / Snapdragon 888 processor
   - 1440 x 3200 resolution

2. **OnePlus 9**
   - Android 11.0 (API 30) / 12.0 (API 31)
   - 8GB RAM
   - Snapdragon 888 processor
   - 1080 x 2400 resolution

3. **Google Pixel 6**
   - Android 12.0 (API 31)
   - 8GB RAM
   - Google Tensor processor
   - 1080 x 2400 resolution

## Android Version Coverage

To ensure compatibility across the supported API range (21-33), include:

- **API 21-22**: Older device or emulator running Android 5.0-5.1 Lollipop
- **API 23-25**: Device running Android 6.0-7.1 Marshmallow/Nougat
- **API 26-27**: Device running Android 8.0-8.1 Oreo
- **API 28-29**: Device running Android 9.0-10.0 Pie/Q
- **API 30-31**: Device running Android 11.0-12.0 R/S
- **API 32-33**: Device running Android 12L-13.0 S/T

## Emulator Configurations

When physical devices are unavailable, use these emulator configurations:

### Low-End Emulator
```
Name: low_end_test
Device: Pixel
API Level: 24
RAM: 1.5GB
CPU/ABI: x86
```

### Mid-Range Emulator
```
Name: mid_range_test
Device: Pixel 3
API Level: 28
RAM: 3GB
CPU/ABI: x86
```

### High-End Emulator
```
Name: high_end_test
Device: Pixel 6
API Level: 31
RAM: 6GB
CPU/ABI: x86_64
```

## Device Setup for Testing

Before testing, ensure all devices meet these requirements:

1. **Fresh Device State**
   - Factory reset or cleared app data
   - No non-essential apps running in background
   - Connected to power source
   - Airplane mode enabled (unless testing network features)
   - Screen brightness set to 50%
   - Developer options enabled

2. **Developer Settings**
   - USB debugging enabled
   - Stay awake while charging enabled
   - GPU rendering profiling enabled
   - Enable monitoring of all processes (dumpsys)

3. **Testing Environment**
   - Stable ambient temperature (20-25°C)
   - Consistent WiFi connection when testing network features
   - No phone calls or notifications during testing

## Device-Specific Performance Targets

| Device Tier | Cold Start | Memory Usage | Frame Rate | Battery Impact |
|-------------|------------|--------------|------------|----------------|
| Low-End     | < 3.5s     | < 100MB      | > 45fps    | < 7% / hour    |
| Mid-Range   | < 2.5s     | < 150MB      | > 55fps    | < 5% / hour    |
| High-End    | < 1.5s     | < 180MB      | > 58fps    | < 4% / hour    |

## Testing Rotation

For thorough testing, follow this device rotation strategy:

1. **Smoke Testing**
   - Test on one device from each tier
   - Ensure basic functionality works

2. **Full Performance Test**
   - Run complete test suite on all devices 
   - Document results per device

3. **Regression Testing**
   - Use a subset of devices (one per tier)
   - Compare with previous baselines