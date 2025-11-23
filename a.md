

| Paper | Conf. / Journal | Year | Dataset | mAP Novel (LVIS / others) | mAP Known (LVIS / others) | Main Limitations |
| --- | --- | --- | --- | --- | --- | --- |
| Sylph: A Hypernetwork Framework for Incremental Few-Shot Object Detection | CVPR | 2022 | COCO, **LVIS v1** | **LVIS rare (APr, novel)**: 13.9 | **LVIS base (approx.)**: 22.3  (APc 19.0, APf 25.5) | Few-shot **supervised** (needs K labeled examples per novel class); heavy meta-training with FCOS + hypernetwork; not training-free; no text or VLM priors; still significantly lower LVIS rare AP than your training-free CoFM (17.4) even with extra training. |
| Incremental Few-Shot Object Detection via Simple Fine-Tuning Approach (iTFA) | IEEE TNNLS | 2023 | VOC, COCO, **LVIS v1** | **LVIS rare (APr, novel)**: 18.1 | **LVIS base (approx.)**: 25.5 (APc 21.0, APf 29.9) | Incremental few-shot **fine-tuning**; requires full base-detector training + extra fine-tuning for each novel step; still supervised on novel classes; no zero-shot ability; no VLM semantic guidance; more complex training schedule than your CoFM pipeline and higher computational cost. |
| **Revisiting Few-Shot Object Detection with Vision-Language Models** | **NeurIPS 2024 (Datasets & Benchmarks)** :contentReference[oaicite:4]{index=4} | 2024 | COCO, **LVIS v1**, nuImages. LVIS-base = frequent+common; LVIS-rare = novel. :contentReference[oaicite:5]{index=5} | **LVIS (10-shot Detic + Pseudo-Negatives):** APr = **19.2** on rare (novel) classes. :contentReference[oaicite:6]{index=6} | **LVIS base:** APf = 34.6, APc = 33.2 (≈ **33.9** AP on known classes on average). :contentReference[oaicite:7]{index=7} | • **Still few-shot supervised:** they fine-tune Detic with a small number of labeled rare-class boxes + federated losses and pseudo-negatives; not training-free like our CoFM pipeline.<br>• **Detic already CLIP-pretrained on LVIS labels:** VLM has seen LVIS classes during training, so higher accuracy partly comes from label overlap, not from true “novel” generalization.<br>• **LVIS metric is (APf, APc, APr), not our mAP@[0.5:0.95] known/novel NOD split:** direct comparison with our LVIS-NOD table is not strictly fair; their goal is improving FSOD benchmarks, not pure NOD.<br>• **Focus shifted to loss design (FedLoss / InvFedLoss) instead of modular multi-model cooperation:** does not explore cooperative VFM+VLM combinations like CLIP+SAM+DINOv2, so it doesn’t directly address our main contribution (cooperative foundational models). |




























## LVIS Novel Object Detection – Literature Summary

| Category        | Paper (short name)                                                                 | Venue / Year | LVIS Setting                                                          | Novel Performance (LVIS)                                                                 | Known / Base Performance (LVIS)                                             | Notes (very short)                                                                 |
|----------------|--------------------------------------------------------------------------------------|-------------|------------------------------------------------------------------------|------------------------------------------------------------------------------------------|------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| **Supervised** | **ViLD** – Open-vocabulary Object Detection via Vision and Language KD              | ICLR 2022   | **LVIS v1.0, OV-LVIS** split (337 rare = novel, 866 freq+common = base) | **APr = 16.6** (rare = novel)                                                           | **APc = 24.6**, **APf = 30.3**, **APall = 25.5**                             | Fully supervised Mask R-CNN on LVIS base + CLIP/ALIGN KD; heavy training, OV-LVIS split. |
| **Supervised** | **OV-DETR** – Open-Vocabulary DETR with Conditional Matching                        | ECCV 2022   | **LVIS v1.0, OV-LVIS** split (same as ViLD)                             | **APr = 17.4**                                                                           | **APc = 25.0**, **APf = 32.5**, **APall = 26.6**                             | Fully trained Deformable DETR + CLIP text matching; OV-LVIS only, not NCDL (80+1123).     |
| **Semi-sup.**  | **SAS-Det** – Taming Self-Training for Open-Vocabulary OD                           | CVPR 2024   | **LVIS v1.0, LVIS-OVD** (rare = novel, common+freq = base)             | **R50-C4:** APr = 20.9, APall = 27.4<br>**R50x4-C4:** APr = 29.1, APall = 33.5          | **R50-C4:** APc = 26.1, APf = 31.6<br>**R50x4-C4:** APc = 32.4, APf = 36.8   | Self-training with pseudo labels from a strong teacher (e.g., RegionCLIP); OV-LVIS split.  |
| **Semi-sup.**  | **PB-OVD** – Open Vocabulary OD with Pseudo Bounding-Box Labels                    | ECCV 2022   | **LVIS v0.5** as *generalization* test set (not LVIS v1.0 OV-LVIS)     | Reports **+2.8 AP** improvement on LVIS over baseline (no APr/APc/APf breakdown given). | – (paper does **not** report separate APc/APf/APr for LVIS)                  | Uses pseudo boxes from image–caption data; only overall LVIS AP gain (+2.8) is reported.  |
| **Unsupervised (NCDL)** | **RNCDL** – Learning to Discover and Detect Objects                       | NeurIPS 2022| **COCO→LVIS NCDL**, LVIS v1.0 val:<br>80 COCO = known, 1123 LVIS = novel | **Novel mAP = 5.42** (HyperAI LVIS v1.0 NOD benchmark)                                   | **Known mAP = 25.00**, **All mAP = 6.92**                                   | Novel-class discovery + detection; very low novel mAP, two-stage pipeline, no VLM priors. |
| **Unsupervised (NCDL)** | **PANDAS** – Prototype-based Novel Class Discovery and Detection          | CoLLAs 2024 | **COCO→LVIS (COCOLVIS)**, LVIS v1.0 val                                | **Novel mAP@0.5 = 8.7** (vs 6.2 for RNCDL on same benchmark)                           | **Base mAP@0.5 = 45.6**, **All mAP@0.5 = 11.5** (plus f/c/r reported in paper) | Prototype-based NCD; improves novel mAP over RNCDL but absolute novel AP remains low.     |




