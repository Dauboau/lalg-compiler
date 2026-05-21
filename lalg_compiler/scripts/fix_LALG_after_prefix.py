from pathlib import Path
import re
p = Path('src/main/javacc/g5/LALG.jj')
s = p.read_text()
# fix missing )) before return;
s = re.sub(r'(sint\.modoPanico\([^\)]*\))\s*return;', lambda m: m.group(1)+') return;', s)
# This may add an extra ) if already correct; ensure no triple ))
s = s.replace('))) return;', ')) return;')
# fix new HashSet with missing ) like new HashSet<Integer>(, sync)
s = re.sub(r'new\s+java\.util\.HashSet<Integer>\(\s*,\s*sync\)', 'new java.util.HashSet<Integer>(), sync', s)
# fix patterns where new HashSet had stray commas: new HashSet<Integer>(, 
s = re.sub(r'new\s+java\.util\.HashSet<Integer>\(\s*,', 'new java.util.HashSet<Integer>(),', s)
# fix cases like new java.util.HashSet<Integer>(, )
s = s.replace('new java.util.HashSet<Integer>(, )', 'new java.util.HashSet<Integer>()')
# ensure if conditions have closing )) before return
s = re.sub(r'if\s*\(\s*(sint\.modoPanico\([^\)]*?)\)\s*return;', lambda m: 'if ('+m.group(1)+')) return;', s)

p.write_text(s)
print('Fixed LALG.jj')
