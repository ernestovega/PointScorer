Roborazzi reference images for `ScreenshotTest`.

- CI (`.github/workflows/tests.yml`) runs `verifyRoborazziDebug` against these PNGs on every PR.
- After an intended UI change, re-record them with the **Record screenshots** workflow
  (Actions tab, pick the branch) or locally with `./gradlew recordRoborazziDebug`.
- Record on the same OS as CI (Linux) to avoid font anti-aliasing differences.
