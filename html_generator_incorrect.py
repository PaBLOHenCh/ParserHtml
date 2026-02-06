import os
import random

NUM_HTML = 30
OUTPUT_DIR = "html_malformed_tests"

TAGS = ["html", "head", "body", "div", "section", "article", "main", "nav", "header", "footer"]

TEXTS = [
    "Texto de teste.",
    "Conteúdo inválido.",
    "Linha profunda.",
    "Outro texto qualquer.",
    "Mais um trecho.",
]

random.seed()  # aleatório de verdade


def indent(level: int) -> str:
    # indentação só para visual; seu analisador deve ignorar
    return " " * (level * 4)


def make_malformed_case(case_type: str) -> str:
    """
    Gera um HTML quebrado seguindo as premissas de linha única por tipo,
    mas introduzindo erros estruturais (tags faltando, ordem errada, etc.).
    """
    lines = []

    if case_type == "missing_closing":
        # Abre várias tags e deixa uma sem fechar
        stack = ["html", "body", "div"]
        for i, t in enumerate(stack):
            lines.append(f"{indent(i)}<{t}>")
        lines.append(f"{indent(len(stack))}{random.choice(TEXTS)}")
        # fecha só parte (deixa <div> aberta)
        lines.append(f"{indent(1)}</body>")
        lines.append(f"{indent(0)}</html>")

    elif case_type == "missing_opening":
        # Fecha uma tag que nunca abriu
        lines.append("<html>")
        lines.append("    <body>")
        lines.append("        </div>")  # fechamento inesperado
        lines.append("        " + random.choice(TEXTS))
        lines.append("    </body>")
        lines.append("</html>")

    elif case_type == "wrong_nesting":
        # Ordem de fechamento errada
        lines.append("<html>")
        lines.append("    <body>")
        lines.append("        <div>")
        lines.append("            " + random.choice(TEXTS))
        lines.append("    </div>")   # fecha div com indentação errada, mas isso não importa
        lines.append("        </body>")  # fecha body antes de fechar corretamente o div (nesting errado)
        lines.append("</html>")

    elif case_type == "mismatched_tag":
        # Abre uma tag e fecha outra
        open_tag = "div"
        close_tag = "section"  # diferente
        lines.append("<html>")
        lines.append("    <body>")
        lines.append(f"        <{open_tag}>")
        lines.append("            " + random.choice(TEXTS))
        lines.append(f"        </{close_tag}>")  # mismatch
        lines.append("    </body>")
        lines.append("</html>")

    elif case_type == "extra_closing":
        # Fecha tags a mais (ex.: fecha html duas vezes)
        lines.append("<html>")
        lines.append("    <body>")
        lines.append("        " + random.choice(TEXTS))
        lines.append("    </body>")
        lines.append("</html>")
        lines.append("</html>")  # extra

    elif case_type == "unclosed_root":
        # Não fecha a raiz html
        lines.append("<html>")
        lines.append("    <body>")
        lines.append("        " + random.choice(TEXTS))
        lines.append("    </body>")
        # sem </html>

    elif case_type == "close_in_wrong_order_deep":
        # Abre várias e fecha no meio errado
        stack = ["html", "body", "div", "section"]
        for i, t in enumerate(stack):
            lines.append(f"{indent(i)}<{t}>")
        lines.append(f"{indent(len(stack))}{random.choice(TEXTS)}")
        # fecha body antes de section/div
        lines.append(f"{indent(1)}</body>")
        lines.append(f"{indent(3)}</section>")
        lines.append(f"{indent(2)}</div>")
        lines.append(f"{indent(0)}</html>")

    else:
        # fallback: mismatch simples
        lines = [
            "<html>",
            "    <body>",
            "        <div>",
            "            " + random.choice(TEXTS),
            "        </span>",  # tag inexistente no stack
            "    </body>",
            "</html>",
        ]

    return "\n".join(lines) + "\n"


def main():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    cases = [
        "missing_closing",
        "missing_opening",
        "wrong_nesting",
        "mismatched_tag",
        "extra_closing",
        "unclosed_root",
        "close_in_wrong_order_deep",
    ]

    for i in range(1, NUM_HTML + 1):
        case_type = random.choice(cases)
        content = make_malformed_case(case_type)

        filename = os.path.join(OUTPUT_DIR, f"malformed_{i:02d}.html")
        with open(filename, "w", encoding="utf-8") as f:
            f.write(content)

    print(f"{NUM_HTML} HTMLs mal-formados gerados em ./{OUTPUT_DIR}")


if __name__ == "__main__":
    main()
