'''~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~'''
import fnmatch
import os
import re

license_txt = """~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~"""

# get the directory of the script
project_dir = os.path.dirname(os.path.realpath(__file__))

# choose files types to include
# choose directories to exclude from search
includes = ['*.java','*.sql', '*.xhtml']
excludes = ['AUTHORS',
			'.gitignore',
			'*.ogg'
			'*.mp3',
			'*.db',
			'*.jsp',
			'*.gif',
			'*.png',
			'*.jpg',
			'*.xls',
			'*.properties',
			'*.MF',
			'*.xml',
            '*.c',
			'*.jar'
			'*.md',
			'*.png',
			'*.svg',
			'*.ico',
			'*.sh',
			'*.icns',
			'*.spec',
			'.git/*',
			'LabCulturaGame/build/*',
			'LabCulturaGame/dist/*',
			'LabCulturaGame/nbproject/*',
			'LabCulturaGame/test/*'
			]

# transform glob patterns to regular expressions
includes = r'|'.join([fnmatch.translate(x) for x in includes])
excludes = r'|'.join([fnmatch.translate(x) for x in excludes]) or r'$.'

def get_files(start_dir, includes, excludes):
	# use os.walk to recursively dig down into the project directory
	match_files = []
	for root, dirs, files in os.walk(start_dir):
		if not re.search(excludes, root):
			files = [f for f in files if re.search(includes, f) and not re.search(excludes, f)]
			files = [os.path.join(root, f) for f in files]
			match_files += files
	return match_files

def write_header(file_name, license_txt):
	# find and replace license header
	# or add new header if not existing
	file_type = os.path.splitext(file_name)[-1]

	if file_type == '.py':
		comment = ["'''\n","\n'''\n"]
		pattern = re.compile("'''~(.+?)~'''", re.DOTALL|re.MULTILINE)

	if file_type == '.c' or '.java' or '.sql':
		comment = ['/*', '*/\n']
		pattern = re.compile('/[*]~(.+?)~[*]/', re.DOTALL|re.MULTILINE)

	if file_type == '.xhtml':
		comment = ['<!--', '-->\n']
		pattern = re.compile('<!--~(.+?)~-->', re.DOTALL|re.MULTILINE)

	license_txt = comment[0] + license_txt + comment[1]
	
	with file(file_name, 'r') as original:
		data = original.read()

	with file(file_name, 'w') as modified:
		if re.findall(pattern, data):
			# if header already exists, then update, but dont add the last newline.
			modified.write(re.sub(pattern, license_txt[:-1], data))
			modified.close()
		else:
			# else write the license header
			modified.write(license_txt + data)
			modified.close()

def update_header():
	# Add a license/docstring header to selected files
	match_files = get_files(project_dir, includes, excludes)
	for f in match_files:
		print f

	for f in match_files:
		write_header(f, license_txt)

if __name__ == '__main__':
	# run update_header() to add headers to find files
	update_header()