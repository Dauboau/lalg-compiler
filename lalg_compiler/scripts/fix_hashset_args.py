from pathlib import Path
s = Path('src/main/javacc/g5/LALG.jj').read_text()
out = ''
i = 0
n = len(s)
while i < n:
    idx = s.find('new HashSet<Integer>(', i)
    if idx == -1:
        out += s[i:]
        break
    out += s[i:idx]
    j = idx + len('new HashSet<Integer>(')
    depth = 1
    k = j
    while k < n and depth > 0:
        if s[k] == '(':
            depth += 1
        elif s[k] == ')':
            depth -= 1
        k += 1
    # content between j and k-1
    content = s[j:k-1]
    # if there's a top-level comma in content (not nested) followed by ' sync' then we need to move ) before that comma
    # scan content for top-level commas
    d = 0
    comma_pos = -1
    for idx2, ch in enumerate(content):
        if ch == '(':
            d += 1
        elif ch == ')':
            d -= 1
        elif ch == ',' and d == 0:
            comma_pos = idx2
            break
    if comma_pos != -1:
        # split
        before = content[:comma_pos].rstrip()
        after = content[comma_pos+1:]
        out += 'new HashSet<Integer>(' + before + '),' + after + ')'
    else:
        out += 'new HashSet<Integer>(' + content + ')'
    i = k

Path('src/main/javacc/g5/LALG.jj').write_text(out)
print('Fixed new HashSet<Integer> arg commas')
