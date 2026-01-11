# Bucket Reach Fix

Paper plugin that fixes block interaction range issues when using buckets.

![Latest Release](https://img.shields.io/github/v/release/putindeer/mcdev-utils?style=flat-square&color=blue)
![License](https://img.shields.io/github/license/putindeer/bucketreachfix?style=flat-square)
![GitHub last commit](https://img.shields.io/github/last-commit/putindeer/bucketreachfix?style=flat-square)

---

## Overview

This plugin adjusts the **block interaction reach** while a player is holding a bucket and restores it when they stop holding one.

It exists to fix a **ViaVersion issue** between **pre-1.20.5 clients on post-1.20.5 servers**, where **water and fluid placement breaks due to missing or incorrect interaction range**.

---

## Behavior

- Holding a bucket temporarily modifies block interaction reach
- Switching away from a bucket restores the default reach
- The reach value is reset on:
  - Item change
  - Inventory interactions
  - Player death
  - Player disconnect

---

## Limitations / TODO

- Interaction reach values are **hardcoded** so custom values for block interaction range WON'T work.
- Further testing on specific plugins/situations.

---

## Build

```bash
mvn clean package
