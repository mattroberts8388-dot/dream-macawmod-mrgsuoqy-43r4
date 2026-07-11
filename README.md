# Macaw Mod

Adds a passive **Macaw** bird to Minecraft 1.20.1 (Fabric):

- Spawns naturally in **jungle biomes**.
- **Tame** it by feeding it seeds (wheat, melon, pumpkin, or beetroot seeds).
- Once tamed, right-click it with an **empty hand** to have it **sit on your shoulder**, just like a parrot.
- While a Macaw rides your shoulder, you gain **Night Vision**.

---

## How to build the mod for FREE using GitHub (no Java or installation needed)

You don't need to install Java, Gradle, or anything on your computer.
GitHub will build the `.jar` file for you in the cloud.

### Step-by-step

1. **Create a GitHub account** at https://github.com (free).
2. **Create a new repository**: click the **+** in the top-right → **New repository**. Give it any name and click **Create repository**.
3. On the new empty repo page, click the link **"uploading an existing file"**.
4. **Extract the downloaded zip** of this mod project on your computer.
5. **⚠️ macOS users — VERY IMPORTANT:** The `.github` folder (which contains the build instructions) is **hidden by default** in Finder. If you don't upload it, the build will **never run**.
   - Open the extracted folder in Finder.
   - Press **Cmd + Shift + .** (Command, Shift, and the period key) to reveal hidden files.
   - You should now see the `.github` folder appear.
6. **Select ALL files and folders from INSIDE the extracted folder** — including the hidden `.github` folder — and **drag them into the GitHub upload page**.
   - ✅ Drag the **contents** of the folder (the files and folders inside it).
   - ❌ Do **NOT** drag the outer folder itself.
7. Scroll down and click **Commit changes**.
8. Click the **Actions** tab at the top of your repository.
9. Wait about **2 minutes** for the build to finish (a green check mark ✅ means success).
10. Click the completed build run, then scroll down to **Artifacts** and download **mod-jar**.
11. Unzip the downloaded artifact — inside is the `macawmod-1.0.0.jar`.
12. Copy that `.jar` into your Minecraft `mods` folder:
    - Windows: `%appdata%\.minecraft\mods`
    - macOS: `~/Library/Application Support/minecraft/mods`
    - Linux: `~/.minecraft/mods`
13. Make sure you have **Fabric Loader** and the **Fabric API** installed for Minecraft **1.20.1**, then launch the game!

---

## Requirements

- Minecraft **1.20.1**
- **Fabric Loader** 0.15.11+
- **Fabric API**

## License

MIT