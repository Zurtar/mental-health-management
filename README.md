# Documentation

The API reference can be found at: [vibe-check-docs](https://zurtar.github.io/mental-health-management/). This is built from the KDoc comments within the application each time the project is built.

# Getting started 

### Option 1: Using the APK 

If you already have android device that meets the requirements you can directly install the APK onto the phone without additional tools or setup. The APK can be found under [releases](https://github.com/Zurtar/mental-health-management/releases)

##### Enabling Unknown App Installation 
Note: You must have the option "Install unknown apps" enabled for the browser you are downloading the APK in, on the **phone**. 

Navigate to Settings > Security (or Apps & Notifications > Special App Access > Install unknown apps) and enable the option for the specific app you want to allow

If you have trouble with this there are a variety of resource online that will let you enable this setting.


### Option 2: Using Android Studio & An Emulator

1. Download and Install Android Studiohttps://developer.android.com/studio
2. Navigate to: **File** > **New** > **Project from Version Control**, 
3. Under Repository URL put the url of this repository 
![image](https://github.com/user-attachments/assets/aa42aaab-76ad-4ca8-9d14-58ef728b59ca)

4. Once the project imports you may be prompted to configure your IDE, unfortunately we cannot provide detailed steps for this portion as it will differ depending on your installation. But in general you should be able to Sync the Gradle project (Ctrl + O) .
5. Run the app, you will be prompted to create a device to emulate, `Medium Phone API 35` is the reccomended choice, SDK of at least 25 is required.
6. Run the project.
