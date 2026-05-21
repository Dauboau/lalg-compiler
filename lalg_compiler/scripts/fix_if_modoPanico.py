from pathlib import Path
s = Path('src/main/javacc/g5/LALG.jj').read_text()
out = []
i = 0
n = len(s)
while i < n:
    idx = s.find('if (sint.modoPanico(', i)
    if idx == -1:
        out.append(s[i:])
        break
    out.append(s[i:idx])
    j = idx + len('if (')
    # find start of sint call
    call_start = s.find('sint.modoPanico(', idx)
    if call_start == -1:
        out.append(s[idx:idx+3])
        i = idx+3
        continue
    # find matching ) for sint call
    k = call_start + len('sint.modoPanico(')
    depth = 1
    while k < n and depth > 0:
        if s[k] == '(':
            depth += 1
        elif s[k] == ')':
            depth -= 1
        k += 1
    # k is position after matching ) of sint call
    # now check if followed by space and 'return;'
    rest = s[k: k+20]
    if rest.lstrip().startswith('return;') or s[k:k+8].strip().startswith('return'):
        # insert an extra ')' before k
        out.append(s[idx:k] + ')' )
        i = k
    else:
        # also handle pattern where there's no space: e.g. ') return;'
        if s[k:k+8].startswith(' return'):
            out.append(s[idx:k] + ')' )
            i = k
        else:
            out.append(s[idx:k])
            i = k

Path('src/main/javacc/g5/LALG.jj').write_text(''.join(out))
print('Fixed if-sint.modoPanico patterns')
