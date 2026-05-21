from pathlib import Path
import re
p=Path('src/main/javacc/g5/LALG.jj')
s=p.read_text()
# find cmd rule
m=re.search(r"void\s+cmd\s*\([^\)]*\)\s*:\s*\{[\s\S]*?\}\s*\n\}", s)
if not m:
    # try broader: find start at 'void cmd' and end at the next '\n}\n' that closes rule; crude fallback
    start = s.find('void cmd(')
    if start==-1:
        print('cmd not found')
        raise SystemExit(1)
    # find the closing ')' of the rule block by counting braces
    i=start
    depth=0
    for k in range(start, len(s)):
        if s[k]=='{':
            depth+=1
        elif s[k]=='}':
            depth-=1
            if depth==0:
                end=k+1
                break
    rule = s[start:end]
else:
    rule = m.group(0)
    start = m.start()
    end = m.end()

# split top-level alternatives inside the rule body by '|' at top level
# find body between first '{' after header and the final '}' before end
body_start = rule.find('{', rule.find(')'))+1
body_end = rule.rfind('}')
body = rule[body_start:body_end]

# split keeping delimiters: we will split on '\n      |' or '\n    |' patterns that indicate alternatives
alts = re.split(r'\n\s*\|', body)

new_alts = []
for alt in alts:
    # determine key token for this alternative
    # look for first occurrence of try { <TOKEN> }
    mtk = re.search(r'try\s*\{\s*<\s*([A-Z0-9_]+)\s*>', alt)
    if mtk:
        token = mtk.group(1)
    else:
        # look for try { <ID> } or try { <NUMERO...>
        mtk2 = re.search(r'try\s*\{\s*<\s*([A-Z0-9_]+)\s*>', alt)
        token = mtk2.group(1) if mtk2 else None
    if token:
        key = token.lower()
        # remove possible prefix SIMB_
        if key.startswith('simb_'):
            key = key[len('simb_'):]
        name = f'cmd_{key}'
    else:
        # fallback
        name = 'cmd_unknown'
    # replace occurrences of sint.modoPanico("cmd", with specific name within this alt
    alt_new = re.sub(r'sint\.modoPanico\("cmd"\s*,', f'sint.modoPanico("{name}",', alt)
    new_alts.append(alt_new)

new_body = '\n|'.join(new_alts)
new_rule = rule[:body_start] + new_body + rule[body_end:]
new_s = s[:start] + new_rule + s[end:]
Path('src/main/javacc/g5/LALG.jj').write_text(new_s)
print('Specialized cmd branches in LALG.jj')
