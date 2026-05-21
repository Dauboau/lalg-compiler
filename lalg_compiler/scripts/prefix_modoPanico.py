import re
from pathlib import Path

p = Path('src/main/javacc/g5/LALG.jj')
text = p.read_text()

out_lines = []
current_proc = None
proc_pattern = re.compile(r'^\s*void\s+(\w+)\s*\(')

for line in text.splitlines(True):
    m = proc_pattern.match(line)
    if m:
        current_proc = m.group(1)
    # replace occurrences of sint.modoPanico( that do NOT already have a string as first arg
    def repl(match):
        inner = match.group(1)
        # if inner starts with '"' then already has name
        stripped = inner.lstrip()
        if stripped.startswith('"'):
            return match.group(0)
        return 'sint.modoPanico("%s", %s' % (current_proc if current_proc else 'unknown', inner)
    line = re.sub(r'sint\.modoPanico\(([^\)]*)\)', repl, line)
    out_lines.append(line)

p.write_text(''.join(out_lines))
print('Updated LALG.jj')
