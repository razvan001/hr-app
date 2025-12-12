# app.py
from fastapi import FastAPI, Request
from pydantic import BaseModel
from transformers import pipeline

app = FastAPI()
paraphraser = pipeline("text2text-generation", model="Vamsi/T5_Paraphrase_Paws")

class TextRequest(BaseModel):
  text: str

@app.post("/polish")
def polish_text(req: TextRequest):
  result = paraphraser(req.text, max_length=128, num_return_sequences=1)
  return {"polished": result[0]["generated_text"]}
