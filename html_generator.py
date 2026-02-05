import random
import string
import os

# ---------------- CONFIGURAÇÕES ----------------
NUM_HTML = 30
MAX_DEPTH = 6
MAX_TEXTS = 5
OUTPUT_DIR = "html_tests"

TAGS = [
    "html", "head", "body", "div", "section",
    "article", "main", "nav", "footer", "header"
]

# -----------------------------------------------

def random_text():
    tamanho = random.randint(10, 40)
    return ''.join(random.choices(
        string.ascii_letters + "     áéíóúãõç",
        k=tamanho
    )).strip().capitalize() + "."

def generate_html(index):
    lines = []
    stack = []

    depth = random.randint(2, MAX_DEPTH)
    num_texts = random.randint(1, MAX_TEXTS)

    # abre tags
    for d in range(depth):
        tag = random.choice(TAGS)
        stack.append(tag)
        lines.append(" " * (d * 4) + f"<{tag}>")

        # chance de colocar texto em níveis diferentes
        if random.random() < 0.4:
            lines.append(" " * ((d + 1) * 4) + random_text())

    # textos extras em profundidade máxima
    for _ in range(num_texts):
        lines.append(" " * (depth * 4) + random_text())

    # fecha tags (ordem correta)
    while stack:
        tag = stack.pop()
        indent = " " * (len(stack) * 4)
        lines.append(indent + f"</{tag}>")

    return "\n".join(lines)

def main():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    for i in range(1, NUM_HTML + 1):
        html = generate_html(i)
        filename = os.path.join(OUTPUT_DIR, f"html_{i:02d}.html")

        with open(filename, "w", encoding="utf-8") as f:
            f.write(html)

    print(f"{NUM_HTML} arquivos HTML gerados em ./{OUTPUT_DIR}")

if __name__ == "__main__":
    main()
